package com.example.sendflow.service.consumer;

import com.example.sendflow.config.RabbitConfig;
import com.example.sendflow.dto.MailMessageDto;
import com.example.sendflow.dto.SmtpConfigDto;
import com.example.sendflow.entity.SendLog;
import com.example.sendflow.enums.EventStatus;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.repository.SendLogRepository;
import com.example.sendflow.service.ISendMailService;
import jakarta.mail.AuthenticationFailedException;
import org.springframework.messaging.handler.annotation.Header;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.mail.internet.AddressException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampaignMailConsumer {
    private final ISendMailService sendMailService;
    private final SendLogRepository logRepository;

    // Tối đa retry 5 lần cho lỗi tạm thời
    private static final int MAX_RETRY_COUNT = 5;
    private static final String HEADER_RETRY_COUNT = "x-retry-count";

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE)
    public void handleSendMailCampaign(MailMessageDto messageDto,
                                       @Header(value = HEADER_RETRY_COUNT, required = false, defaultValue = "0")
                                       int retryCount
    ) {
        System.out.println(" [RabbitMQ] Nhận được message từ queue MAIL_QUEUE: " + messageDto);

        SendLog sendLog = logRepository.findById((Long) messageDto.getSendLogId())
                .orElseThrow(() -> new ResourceNotFoundException("Send log not found"));
        if(sendLog==null) return;
        try {
            String html = buildTrackingHtml(sendLog.getId(), messageDto.getHtml());
            // Gửi email
            sendMailService.sendMailWithSmtp(
                    messageDto.getSmtpConfig(),
                    messageDto.getFromEmail(),
                    messageDto.getToEmail(),
                    messageDto.getSubject(),
                    html
            );
            // update send log
            sendLog.setStatus(EventStatus.SENT);
            sendLog.setCreatedAt(LocalDateTime.now());
            logRepository.save(sendLog);
            log.info("Email SENT successfully → {}", messageDto.getToEmail());
        } catch (MessagingException e){
            if(retryCount>=MAX_RETRY_COUNT) {
                handlePermanentFailure(sendLog, "Max retries exceeded: " + e.getMessage());
                log.error("Email FAILED permanently after {} retries → {}", retryCount, messageDto.getToEmail());
            }else {
                handleRetryableFailure(sendLog, e.getMessage());
                log.warn("Temporary failure (retry {}/{}): {}", retryCount + 1, MAX_RETRY_COUNT, e.getMessage());
                // Throw để RabbitMQ requeue với header retry tăng lên
                throw new RuntimeException("Temporary send failure - retry", e);
            }
        }catch (Exception e){
            handlePermanentFailure(sendLog, "Unexpected error: " + e.getMessage());
            log.error("Unexpected error for sendLog {}", messageDto.getSendLogId(), e);
        }
    }

    private void handlePermanentFailure(SendLog log, String error) {
        log.setStatus(EventStatus.FAILED_PERMANENT);
        log.setErrorMessage(error.substring(0, Math.min(error.length(), 500)));
        logRepository.save(log);
        // Có thể gửi alert cho user ở đây
    }

    private void handleRetryableFailure(SendLog log, String error) {
        log.setStatus(EventStatus.FAILED);
        log.setErrorMessage(error.substring(0, Math.min(error.length(), 500)));
        logRepository.save(log);
    }

    private String buildTrackingHtml(Long sendLogId, String content) {
        return content +
               "<br><br>" +
               "<a href='http://localhost:8080/send-flow/api/tracking/click/" + sendLogId + "'>Click vào đây</a>" +
               "<img src='https://sendflow.loca.lt/send-flow/api/tracking/open/" + sendLogId + "' width='1' height='1' />";
    }

}
