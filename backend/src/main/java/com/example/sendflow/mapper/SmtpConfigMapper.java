package com.example.sendflow.mapper;

import com.example.sendflow.dto.SmtpConfigDto;
import com.example.sendflow.entity.SmtpConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SmtpConfigMapper {
    SmtpConfig toSmtpConfig(SmtpConfigDto configDto);
    SmtpConfigDto toSmtpConfigDto(SmtpConfig config);
}
