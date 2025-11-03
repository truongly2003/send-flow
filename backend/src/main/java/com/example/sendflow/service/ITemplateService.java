package com.example.sendflow.service;

import com.example.sendflow.dto.request.TemplateRequest;
import com.example.sendflow.dto.response.TemplateResponse;
import com.example.sendflow.entity.Template;

import java.util.List;

public interface ITemplateService {
    List<TemplateResponse> getTemplates(Long userId);
    TemplateResponse getTemplate(Long templateId);
    TemplateResponse createTemplate(TemplateRequest templateRequest);
    TemplateResponse updateTemplate(Long templateId, TemplateRequest templateRequest);
    void deleteTemplate(Long templateId);
}
