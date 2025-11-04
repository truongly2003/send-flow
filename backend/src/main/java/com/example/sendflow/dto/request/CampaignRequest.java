package com.example.sendflow.dto.request;

import com.example.sendflow.enums.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRequest {
    private Long userId;
    private Long contactListId;
    private Long templateId;
    private String name;
    private String messageContent;
    private LocalDateTime scheduleTime;
    private CampaignStatus status;
}
