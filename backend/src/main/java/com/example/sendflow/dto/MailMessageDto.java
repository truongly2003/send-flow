package com.example.sendflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailMessageDto {
    private String subject;
    private String fromEmail;
    private String toEmail;
    private String html;
    private Long sendLogId;
    private SmtpConfigDto smtpConfig;
}
