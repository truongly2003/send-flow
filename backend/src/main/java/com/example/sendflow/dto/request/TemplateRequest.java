package com.example.sendflow.dto.request;

import com.example.sendflow.enums.TemplateCategory;
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
    private Long userId;
    private String name;
    private String subject;
    private String body;
    private TemplateType type;
    private TemplateCategory category;
}
