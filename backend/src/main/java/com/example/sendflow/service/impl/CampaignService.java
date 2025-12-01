package com.example.sendflow.service.impl;

import com.example.sendflow.config.RabbitConfig;
import com.example.sendflow.dto.MailMessageDto;
import com.example.sendflow.dto.request.CampaignRequest;
import com.example.sendflow.dto.response.CampaignResponse;
import com.example.sendflow.entity.*;
import com.example.sendflow.enums.CampaignStatus;
import com.example.sendflow.enums.EventStatus;
import com.example.sendflow.enums.SubscriptionStatus;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.mapper.CampaignMapper;
import com.example.sendflow.repository.*;
import com.example.sendflow.service.ICampaignService;
import com.example.sendflow.service.ISmtpConfigService;
import com.example.sendflow.service.IUsageService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignService implements ICampaignService {
    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;
    private final SubscriptionRepository subscriptionRepository;
    private final UsageRepository usageRepository;
    private final SendLogRepository sendLogRepository;
    private final ISmtpConfigService smtpConfigService;
    private final IUsageService usageService;
    private final ContactListRepository contactListRepository;
    private final TemplateRepository templateRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<CampaignResponse> getAllCampaigns(Long userId) {
        return campaignRepository.findCampaignResponsesByUserId(userId);
    }

    @Override
    @Transactional
    public void createCampaign(CampaignRequest campaignRequest) {

        // 1. get subscription active
        Long userId = campaignRequest.getUserId();
        Subscription subscription = subscriptionRepository.findActiveSubscription(
                userId, SubscriptionStatus.ACTIVE, LocalDateTime.now()
        ).orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        Plan plan = subscription.getPlan();

        // 2. get usage
        String currentPeriod = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        Usage usage = usageRepository.findBySubscriptionId(subscription.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usage not found for subscription"));

        // 3. check campaign quota
        if (usage.getMaxCampaign() != null &&
            usage.getMaxCampaign() <= usage.getCampaignCount()) {
            throw new RuntimeException("Maximum campaigns per month exceeded");
        }

        // 4. create campaign
        Campaign campaign = campaignMapper.toCampaign(campaignRequest);
        campaign.setSubscription(subscription);
        Campaign campaignSaved = campaignRepository.save(campaign);

        // 5. increment usage campaign
        usage.setCampaignCount(usage.getCampaignCount() + 1);
        usage.setUpdatedAt(LocalDateTime.now());
        usageRepository.save(usage);
    }

    // campaign send mail
    @Override
    @Transactional
    public void sendCampaignMail(Long campaignId) {

        // 1. check campaign
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));
        Subscription subscription = campaign.getSubscription();

        // 2. get usage
        Usage usage = usageRepository.findBySubscriptionId(subscription.getId())
                .orElseThrow(() -> new RuntimeException("Usage not found for subscription"));

        // 3. check quota
        List<Contact> contacts = campaign.getContactList().getContacts();
        int totalContacts = usage.getEmailCount() + contacts.size();
        if (usage.getMaxEmail() != null
            && usage.getMaxEmail() <= totalContacts
        ) {
            throw new RuntimeException("Maximum mail per month exceeded");
        }

        // 4. update campaign
        campaign.setStatus(CampaignStatus.SENDING);
        campaign.setCreatedAt(LocalDateTime.now());
        campaignRepository.save(campaign);

        // 5. iterate contact list: create send log + push in rabbitMQ
        boolean hasError = false;
        for (Contact contact : contacts) {
            try {
                SendLog sendLog = new SendLog();
                sendLog.setCampaign(campaign);
                sendLog.setContact(contact);
                sendLog.setRecipientEmail(contact.getEmail());
                sendLog.setStatus(EventStatus.NEW);
                sendLogRepository.save(sendLog);
                // smtp
                SmtpConfig smtpConfig = campaign.getUser().getSmtpConfig();
                // push in rabbitMQ
                MailMessageDto messageDto = MailMessageDto.builder()
                        .sendLogId(sendLog.getId())
                        .fromEmail(smtpConfig.getUsernameSmtp())
                        .toEmail(contact.getEmail())
                        .subject(campaign.getName())
                        .html(campaign.getMessageContent())
                        .smtpConfig(smtpConfigService.getSmtpConfig(campaign.getUser().getId()))
                        .build();

                rabbitTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE,
                        RabbitConfig.MAIL_ROUTING_KEY, messageDto);
            } catch (Exception e) {
                hasError = true;
            }

            // 6. update usage
            usage.setEmailCount(usage.getEmailCount() + contacts.size());
            usage.setUpdatedAt(LocalDateTime.now());
            usageRepository.save(usage);

            // 7. update campaign
            campaign.setStatus(hasError ? CampaignStatus.FAILED : CampaignStatus.COMPLETED);
            campaign.setUpdatedAt(LocalDateTime.now());
            campaignRepository.save(campaign);

        }
    }

    @Override
    public void updateCampaign(Long campaignId, CampaignRequest campaignRequest) {
        Campaign existingCampaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));
        ContactList contactList = contactListRepository.findById(campaignRequest.getContactListId())
                .orElseThrow(() -> new ResourceNotFoundException("ContactList not found"));
        Template template = templateRepository.findById(campaignRequest.getTemplateId())
                .orElseThrow(() -> new ResourceNotFoundException("Template not found"));
        existingCampaign.setUpdatedAt(LocalDateTime.now());
        existingCampaign.setContactList(contactList);
        existingCampaign.setTemplate(template);
        existingCampaign.setMessageContent(campaignRequest.getMessageContent());
        existingCampaign.setName(campaignRequest.getName());
        Campaign campaignSaved = campaignRepository.save(existingCampaign);
    }

    @Override
    public void deleteCampaign(Long campaignId) {
        // 1. check campaign
        Campaign existingCampaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));
        // 2. check status
        if (existingCampaign.getStatus() != CampaignStatus.DRAFT &&
            existingCampaign.getStatus() != CampaignStatus.SCHEDULED) {
            throw new RuntimeException("Cannot delete a campaign that has already been sent");
        }
        // 3. check subscription
        User user = existingCampaign.getUser();
        Subscription subscription = subscriptionRepository.findActiveSubscription(
                user.getId(), SubscriptionStatus.ACTIVE, LocalDateTime.now()
        ).orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        // 4. check usage
        String currentPeriod = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        Usage usage = usageRepository.findBySubscriptionId(subscription.getId())
                .orElseThrow(() -> new RuntimeException("Usage not found for subscription"));
        // 5. update usage
        usage.setCampaignCount(usage.getCampaignCount() - 1);
        usage.setUpdatedAt(LocalDateTime.now());
        usageRepository.save(usage);
        // 6. delete
        campaignRepository.delete(existingCampaign);
    }
}
