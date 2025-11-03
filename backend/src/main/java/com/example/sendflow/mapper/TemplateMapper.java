package com.example.sendflow.mapper;

import com.example.sendflow.dto.request.CampaignRequest;
import com.example.sendflow.dto.request.TemplateRequest;
import com.example.sendflow.dto.response.TemplateResponse;
import com.example.sendflow.entity.Campaign;
import com.example.sendflow.entity.Template;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TemplateMapper {
    Template toTemplate(TemplateRequest templateRequest);
    TemplateResponse toTemplateResponse(Template template);
    void updateCampaign(TemplateRequest templateRequest, @MappingTarget Template existingTemplate);

}
