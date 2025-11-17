package com.example.sendflow.dto.response;

import com.example.sendflow.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardUserResponse {
    private long limitMail; // hạn mức gửi mail
    private long useMail; // đã dùng
    private long totalEmail;
    private long totalContacts;
    private long totalCampaigns;
    private long totalTemplate;
    private double totalCampaignRating; // tỷ lệ gửi chiến dịch
}
