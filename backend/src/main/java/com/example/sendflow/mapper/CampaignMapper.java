package com.example.sendflow.mapper;

import com.example.sendflow.dto.request.CampaignRequest;
import com.example.sendflow.dto.response.CampaignResponse;
import com.example.sendflow.entity.Campaign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CampaignMapper {
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "contactList.id", source = "contactListId")
    @Mapping(target = "template.id", source = "templateId")
    Campaign toCampaign(CampaignRequest campaignRequest);

    @Mapping(target = "userId", source = "user.id")
    CampaignResponse toCampaignResponse(Campaign campaign);

    void updateCampaign(CampaignRequest campaignRequest, @MappingTarget Campaign existingCampaign);
}
