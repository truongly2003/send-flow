package com.example.sendflow.service.impl;

import com.example.sendflow.config.RabbitConfig;
import com.example.sendflow.dto.MailMessageDto;
import com.example.sendflow.dto.SmtpConfigDto;
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
import java.util.Map;
import java.util.stream.Collectors;

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
        Usage usage = usageRepository.findBySubscriptionAndPeriod(
                subscription.getId(), currentPeriod
        ).orElseGet(() -> usageService.createUsage(subscription, currentPeriod));

        // 3. check campaign quota
        if (plan.getMaxCampaignsPerMonth() != null &&
            plan.getMaxCampaignsPerMonth() <= usage.getCampaignCount()) {
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
        Plan plan = subscription.getPlan();

        // 2. get usage
        String currentPeriod = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        Usage usage = usageRepository.findBySubscriptionAndPeriod(subscription.getId(), currentPeriod)
                .orElseThrow(() -> new RuntimeException("Usage record not found"));
        // 3. check quota
        List<Contact> contacts = campaign.getContactList().getContacts();
        int totalContacts = usage.getEmailCount() + contacts.size();
        if (plan.getMaxCampaignsPerMonth() != null
            && plan.getMaxEmailsPerMonth() <= totalContacts
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
        campaignMapper.updateCampaign(campaignRequest, existingCampaign);
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
        Usage usage = usageRepository.findBySubscriptionAndPeriod(subscription.getId(), currentPeriod)
                .orElseThrow(() -> new RuntimeException("Usage record not found"));
        // 5. update usage
        usage.setCampaignCount(usage.getCampaignCount() - 1);
        usage.setUpdatedAt(LocalDateTime.now());
        usageRepository.save(usage);
        // 6. delete
        campaignRepository.delete(existingCampaign);
    }
}
