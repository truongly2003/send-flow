package com.example.sendflow.service.impl;

import com.example.sendflow.dto.SmtpConfigDto;
import com.example.sendflow.entity.SmtpConfig;
import com.example.sendflow.mapper.SmtpConfigMapper;
import com.example.sendflow.repository.SmtpConfigRepository;
import com.example.sendflow.service.ISmtpConfigService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmtpConfigService implements ISmtpConfigService {
    private final SmtpConfigMapper smtpConfigMapper;
    private final SmtpConfigRepository smtpConfigRepository;

    @Override
    public SmtpConfigDto getSmtpConfig(Long userId) {
        SmtpConfig smtpConfig = smtpConfigRepository.findByUserId(userId);
        log.info(smtpConfig.getUsernameSmtp());
        SmtpConfigDto smtpConfigDto = new SmtpConfigDto();
        smtpConfigDto.setSmtpHost(smtpConfig.getSmtpHost());
        smtpConfigDto.setSmtpPort(smtpConfig.getSmtpPort());
        smtpConfigDto.setUsernameSmtp(smtpConfig.getUsernameSmtp());
        smtpConfigDto.setPasswordSmtp(smtpConfig.getPasswordSmtp());
        smtpConfigDto.setAuth(true);
        smtpConfigDto.setStarttls(smtpConfig.isStarttls());
        return smtpConfigDto;
    }

    @Override
    public SmtpConfigDto saveSmtpConfig(SmtpConfigDto smtpConfigDto) {
        SmtpConfig config=smtpConfigMapper.toSmtpConfig(smtpConfigDto);
        SmtpConfig saveSmtpConfig=smtpConfigRepository.save(config);
        return smtpConfigMapper.toSmtpConfigDto(saveSmtpConfig);
    }

    @Override
    public void testConnection(SmtpConfigDto smtpConfigDto) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");

            if ("SSL".equalsIgnoreCase(smtpConfigDto.getEncryption())) {
                props.put("mail.smtp.ssl.enable", "true");
            } else if ("TLS".equalsIgnoreCase(smtpConfigDto.getEncryption())) {
                props.put("mail.smtp.starttls.enable", "true");
            }
            props.put("mail.smtp.host", smtpConfigDto.getSmtpHost());
            props.put("mail.smtp.port", smtpConfigDto.getSmtpPort());

            Session session = Session.getInstance(props);
            Transport transport = session.getTransport("smtp");

            transport.connect(
                    smtpConfigDto.getSmtpHost(),
                    smtpConfigDto.getFromEmail(),
                    smtpConfigDto.getPasswordSmtp()
            );
            transport.close();
        } catch (jakarta.mail.AuthenticationFailedException ex) {
            throw new RuntimeException("Sai username hoặc mật khẩu SMTP (Google yêu cầu App Password).");
        }
        catch (Exception e) {
            throw new RuntimeException("Lỗi kết nối SMTP: " + e.getMessage());
        }
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
