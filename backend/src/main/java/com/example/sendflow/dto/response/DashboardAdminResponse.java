package com.example.sendflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardAdminResponse {
    private long totalUsers;
    private long totalCampaigns;
    private long totalRevenue;
    private long emailsSent;
    private long activeSubscriptions;
    private long successRate;

}
