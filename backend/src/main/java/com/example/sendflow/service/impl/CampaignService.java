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
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService implements ICampaignService {
    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;
    private final SubscriptionRepository subscriptionRepository;
    private final UsageRepository usageRepository;
    private final SendLogRepository sendLogRepository;
    private final ISmtpConfigService smtpConfigService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<CampaignResponse> getAllCampaigns(Long userId) {
        return campaignRepository.findCampaignResponsesByUserId(userId);
    }

    // kiểm tra trước khi tạo chiến dịch
    private void checkSendCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        Long userId = campaign.getUser().getId();
        // kiểm tra gói
        Subscription subscription = subscriptionRepository.findActiveSubscription(userId, SubscriptionStatus.ACTIVE, LocalDateTime.now())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        Plan plan = subscription.getPlan();
        String currentPeriod = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        Usage usage = usageRepository.findBySubscriptionAndPeriod(subscription.getId(), currentPeriod)
                .orElseGet(() -> {
                    Usage newUsage = new Usage();
                    newUsage.setSubscription(subscription);
                    newUsage.setPeriod(currentPeriod);
                    newUsage.setEmailCount(0);
                    newUsage.setContactCount(0);
                    newUsage.setCampaignCount(0);
                    newUsage.setTemplateCount(0);
                    usageRepository.save(newUsage);
                    return newUsage;
                });
        int emailToSend = campaign.getContactList().getContacts().size();
        int totalAfterSend = usage.getEmailCount() + emailToSend;

        if (plan.getMaxEmailsPerMonth() != null && totalAfterSend > plan.getMaxEmailsPerMonth()) {
            throw new RuntimeException("Maximum emails per month exceeded");
        }
        return;
    }

    @Override
    public void createCampaign(CampaignRequest campaignRequest) {
        Campaign campaign = campaignMapper.toCampaign(campaignRequest);
        Campaign campaignSaved = campaignRepository.save(campaign);
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
        Campaign existingCampaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));
        campaignRepository.delete(existingCampaign);
    }

    // chiến dịch gửi mail
    @Override
    public void sendCampaignMail(Long campaignId) {
        // kiểm tra campaign
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));
        // cập nhật / bắt đầu gửi
        campaign.setStatus(CampaignStatus.SENDING);
        campaignRepository.save(campaign);
        // lặp qua danh sách cần gửi
        List<Contact> contacts = campaign.getContactList().getContacts();
        for (Contact contact : contacts) {
            SendLog sendLog = new SendLog();

            sendLog.setCampaign(campaign);
            sendLog.setContact(contact);
            sendLog.setRecipientEmail(contact.getEmail());
            sendLog.setStatus(EventStatus.NEW);
            sendLogRepository.save(sendLog);

            SmtpConfig smtpConfig=campaign.getUser().getSmtpConfig();

            MailMessageDto messageDto=MailMessageDto.builder()
                    .sendLogId(sendLog.getId())
                    .fromEmail(smtpConfig.getUsernameSmtp())
                    .toEmail(contact.getEmail())
                    .subject(campaign.getName())
                    .html(campaign.getMessageContent())
                    .smtpConfig(smtpConfigService.getSmtpConfig(campaign.getUser().getId()))
                    .build();

            rabbitTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE, RabbitConfig.MAIL_ROUTING_KEY, messageDto);
        }
    }
}
