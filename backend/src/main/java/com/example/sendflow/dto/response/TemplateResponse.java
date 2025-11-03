package com.example.sendflow.dto.response;

import com.example.sendflow.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateResponse {
    private Long id;
    private String templateName;
    private String subject;
    private String body;
    private TemplateType templateType;
    private LocalDateTime createdAt;
    private Integer usageCount;
}
