package com.example.sendflow.service.consumer;

import com.example.sendflow.config.RabbitConfig;
import com.example.sendflow.dto.MailMessageDto;
import com.example.sendflow.dto.SmtpConfigDto;
import com.example.sendflow.entity.SendLog;
import com.example.sendflow.enums.EventStatus;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.repository.SendLogRepository;
import com.example.sendflow.service.ISendMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CampaignMailConsumer {
    private final ISendMailService sendMailService;
    private final SendLogRepository logRepository;

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE)
    public void handleSendMailCampaign(MailMessageDto messageDto) {  // Sửa: Nhận Map thay vì String
        System.out.println(" [RabbitMQ] Nhận được message từ queue MAIL_QUEUE: " + messageDto);
        SendLog sendLog = logRepository.findById((Long) messageDto.getSendLogId())
                .orElseThrow(() -> new ResourceNotFoundException("Send log not found"));
        try {
            // Gửi email
            sendMailService.sendMailWithSmtp(
                    messageDto.getSmtpConfig(),
                    messageDto.getFromEmail(),
                    messageDto.getToEmail(),
                    messageDto.getSubject(),
                    messageDto.getHtml()
            );
            // Cập nhật log thành công (giả sử có SendLogService)
            sendLog.setStatus(EventStatus.SENT);
            logRepository.save(sendLog);
            System.out.println("Gửi thành công: " + messageDto.getToEmail());
        } catch (Exception e) {
            sendLog.setStatus(EventStatus.FAILED);
            logRepository.save(sendLog);
            System.out.println("Lỗi gửi: " + e.getMessage());
        }
    }


}
