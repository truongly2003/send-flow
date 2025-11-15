package com.example.sendflow.service;

import com.example.sendflow.dto.request.CampaignRequest;
import com.example.sendflow.dto.response.CampaignResponse;

import java.util.List;

public interface ICampaignService {

    List<CampaignResponse> getAllCampaigns(Long userId);

    void createCampaign(CampaignRequest campaignRequest);

    void updateCampaign(Long campaignId, CampaignRequest campaignRequest);

    void deleteCampaign(Long campaignId);

    void sendCampaignMail(Long campaignId);
}
