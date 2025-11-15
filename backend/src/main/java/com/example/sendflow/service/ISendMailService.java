package com.example.sendflow.service;

import com.example.sendflow.dto.SmtpConfigDto;
import jakarta.mail.internet.AddressException;

public interface ISendMailService {
    // send mail campaign
    void sendMailWithSmtp(SmtpConfigDto smtpConfig, String fromEmail, String toEmail, String subject, String html) throws AddressException;


}
