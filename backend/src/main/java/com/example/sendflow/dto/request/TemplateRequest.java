package com.example.sendflow.dto.request;

import com.example.sendflow.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateRequest {
    private String templateName;
    private String subject;
    private String body;
    private TemplateType templateType;
}
