package com.example.sendflow.dto.response;

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
public class CampaignResponse {
    private Long id;
    private Long userId;
    private String name;
    private String messageContent;
    private String templateName;
    private String contactListName;
    private Long sentCount;
    private Long receivedCount;
    private LocalDateTime scheduleTime;
    private LocalDateTime createdAt;
    private CampaignStatus status;
}
