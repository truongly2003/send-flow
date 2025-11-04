package com.example.sendflow.service.impl;

import com.example.sendflow.dto.request.TemplateRequest;
import com.example.sendflow.dto.response.TemplateResponse;
import com.example.sendflow.entity.Template;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.mapper.TemplateMapper;
import com.example.sendflow.repository.CampaignRepository;
import com.example.sendflow.repository.TemplateRepository;
import com.example.sendflow.service.ITemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateService implements ITemplateService {
    private final TemplateRepository templateRepository;
    private final CampaignRepository campaignRepository;
    private final TemplateMapper templateMapper;

    @Override
    public List<TemplateResponse> getTemplates(Long userId) {
        List<Template> templates = templateRepository.findAllByUserId(userId);
        return templates.stream().map((template -> {
                    TemplateResponse templateResponse = templateMapper.toTemplateResponse(template);
                    templateResponse.setUsageCount(campaignRepository.countByTemplateId(template.getId()));
                    return templateResponse;
                }))
                .collect(Collectors.toList());
    }

    @Override
    public TemplateResponse getTemplate(Long templateId) {
        Template template = templateRepository.findById(templateId).orElseThrow(
                () -> new ResourceNotFoundException("Template not found"));
        return templateMapper.toTemplateResponse(template);
    }

    @Override
    public TemplateResponse createTemplate(TemplateRequest templateRequest) {
        Template template = templateMapper.toTemplate(templateRequest);
        Template savedTemplate = templateRepository.save(template);
        return templateMapper.toTemplateResponse(savedTemplate);
    }

    @Override
    public TemplateResponse updateTemplate(Long templateId, TemplateRequest templateRequest) {
        Template existingTemplate = templateRepository.findById(templateId).orElseThrow(
                () -> new ResourceNotFoundException("Template not found"));
        templateMapper.updateCampaign(templateRequest, existingTemplate);
        Template updateTemplate = templateRepository.save(existingTemplate);
        return templateMapper.toTemplateResponse(updateTemplate);
    }

    @Override
    public void deleteTemplate(Long templateId) {
        Template template = templateRepository.findById(templateId).orElseThrow(
                () -> new ResourceNotFoundException("Template not found"));
        templateRepository.delete(template);
    }
}
