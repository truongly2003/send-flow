package com.example.sendflow.service;

import com.example.sendflow.dto.response.PagedResponse;
import com.example.sendflow.dto.response.SendLogResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISendLogService {
    PagedResponse<SendLogResponse> getAllSendLogByCampaignId(Long campaignId, Pageable pageable);
}
