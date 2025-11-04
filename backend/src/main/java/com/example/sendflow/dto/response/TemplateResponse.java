package com.example.sendflow.dto.response;

import com.example.sendflow.enums.TemplateCategory;
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
    private String name;
    private String subject;
    private String body;
    private TemplateType type;
    private LocalDateTime createdAt;
    private TemplateCategory category;
    private Integer usageCount;
}
