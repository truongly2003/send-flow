package com.example.sendflow.service.impl;

import com.example.sendflow.dto.SmtpConfigDto;
import com.example.sendflow.entity.SmtpConfig;
import com.example.sendflow.repository.SmtpConfigRepository;
import com.example.sendflow.service.ISmtpConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpConfigService implements ISmtpConfigService {
    private final SmtpConfigRepository smtpConfigRepository;

    @Override
    public SmtpConfigDto getSmtpConfig(Long userId) {
        SmtpConfig smtpConfig = smtpConfigRepository.findByUserId(userId);
        SmtpConfigDto smtpConfigDto = new SmtpConfigDto();
        smtpConfigDto.setSmtpHost(smtpConfig.getSmtpHost());
        smtpConfigDto.setSmtpPort(smtpConfig.getSmtpPort());
        smtpConfigDto.setUsernameSmtp(smtpConfig.getUsernameSmtp());
        smtpConfigDto.setPasswordSmtp(smtpConfig.getPasswordSmtp());
        smtpConfigDto.setAuth(true);
        smtpConfigDto.setStarttls(smtpConfig.isStarttls());
        return smtpConfigDto;
    }

    private void saveOrUpdateSmtp(Long userId, SmtpConfigDto dto) {
        SmtpConfig smtp = smtpConfigRepository.findByUserId(userId);
        smtp.setUser(smtp.getUser());
        smtp.setUsernameSmtp(dto.getUsernameSmtp());
        smtp.setPasswordSmtp(dto.getPasswordSmtp());
        smtp.setAuth(dto.getAuth());
        smtp.setStarttls(dto.getStarttls());
        smtpConfigRepository.save(smtp);
    }

}
