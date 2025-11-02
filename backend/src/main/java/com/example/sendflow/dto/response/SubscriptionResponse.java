package com.example.sendflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponse {
    private Long id;
    private Long userId;
    private Long planId;
    private String planName;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
