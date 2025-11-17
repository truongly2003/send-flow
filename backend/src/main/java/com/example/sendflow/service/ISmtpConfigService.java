package com.example.sendflow.service;

import com.example.sendflow.dto.SmtpConfigDto;
import com.example.sendflow.entity.User;

public interface ISmtpConfigService {
    SmtpConfigDto getSmtpConfig(Long userId);
    SmtpConfigDto saveSmtpConfig(SmtpConfigDto smtpConfigDto);
    void testConnection(SmtpConfigDto smtpConfigDto);
}
