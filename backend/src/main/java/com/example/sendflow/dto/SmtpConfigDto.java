package com.example.sendflow.dto;

import com.example.sendflow.entity.SmtpConfig;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SmtpConfigDto {
    private String smtpHost;
    private Integer smtpPort;
    private Boolean auth;
    private Boolean starttls;
    private String usernameSmtp;
    private String passwordSmtp;
    private String fromName;
    private String fromEmail;
}
