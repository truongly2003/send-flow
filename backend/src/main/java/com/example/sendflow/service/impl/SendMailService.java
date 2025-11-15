package com.example.sendflow.service.impl;

import com.example.sendflow.dto.SmtpConfigDto;

import com.example.sendflow.service.ISendMailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.mail.*;

import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendMailService implements ISendMailService {
    @Override
    public void sendMailWithSmtp(SmtpConfigDto smtp, String fromEmail,
                                 String toEmail, String subject, String html) {
        try {
            // cấu hình smtp
            Properties props = new Properties();
            props.put("mail.smtp.auth", smtp.getAuth());
            props.put("mail.smtp.starttls.enable", smtp.getStarttls());
            props.put("mail.smtp.host", smtp.getSmtpHost());
            props.put("mail.smtp.port", smtp.getSmtpPort());
            // tạo session xác thực
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtp.getUsernameSmtp(), smtp.getPasswordSmtp());
                }
            });
            // tạo mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(html, "text/html; charset=utf-8");
            // gửi
            Transport.send(message);
            log.info("Email sent successfully to {}", toEmail);
        } catch (AuthenticationFailedException e) {
            log.error(" SMTP Authentication failed: {}", e.getMessage());
            throw new RuntimeException("Invalid SMTP credentials: " + e.getMessage());
        } catch (MessagingException e) {
            log.error("Email send failed to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Email send failed: " + e.getMessage());
        }

    }
}
