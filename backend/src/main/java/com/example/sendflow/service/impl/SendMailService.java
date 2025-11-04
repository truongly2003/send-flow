package com.example.sendflow.service.impl;

import com.example.sendflow.entity.*;
import com.example.sendflow.enums.CampaignStatus;
import com.example.sendflow.enums.EventStatus;
import com.example.sendflow.enums.SubscriptionStatus;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.repository.*;
import com.example.sendflow.service.ISendMailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendMailService implements ISendMailService {
    private final CampaignRepository campaignRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UsageRepository usageRepository;
    private final JavaMailSender mailSender;
    private final SendLogRepository sendLogRepository;

    @Override
    public void sendCampaignMail(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        Long userId = campaign.getUser().getId();
        // kiểm tra gói
        Subscription subscription = subscriptionRepository.findActiveSubscription(userId, SubscriptionStatus.ACTIVE, LocalDateTime.now())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        Plan plan = subscription.getPlan();

        // kiểm tra số lượng mail trong tháng
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
        campaign.setStatus(CampaignStatus.SENDING);
        campaignRepository.save(campaign);

        int successCount = 0;
        int failCount = 0;

        for (Contact contact : campaign.getContactList().getContacts()) {
            SendLog sendLog = new SendLog();
            sendLog.setCampaign(campaign);
            sendLog.setContact(contact);
            sendLog.setStatus(EventStatus.SENDING);
            try {
                sendMail(contact.getEmail(), campaign.getName(), campaign.getMessageContent());
                sendLog.setStatus(EventStatus.SENT);
                sendLog.setDeliveredAt(LocalDateTime.now());
                successCount++;
                log.info("✅ Sent to {}", contact.getEmail());
            } catch (Exception e) {
                sendLog.setStatus(EventStatus.FAILED);
                sendLog.setErrorMessage(e.getMessage());
                failCount++;
                log.error("Error sending mail to {}: {}", contact.getEmail(), e.getMessage());
                e.printStackTrace();
            }
            sendLogRepository.save(sendLog);
        }
        // cập nhật usage
        usage.setEmailCount(usage.getEmailCount() + emailToSend);
        usageRepository.save(usage);

        // cập nhật campaign
        campaign.setStatus(CampaignStatus.COMPLETED);
        campaignRepository.save(campaign);
        log.info("Campaign '{}' completed. Success: {}, Failed: {}",
                campaign.getName(), successCount, failCount);

    }

    private void sendMail(String to, String subject, String content) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
