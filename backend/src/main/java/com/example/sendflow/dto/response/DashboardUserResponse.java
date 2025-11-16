package com.example.sendflow.dto.response;

import com.example.sendflow.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardUserResponse {
    // Subscription / Plan
    private String planName;
    private String planStatus; // active, expiring, expired
    private int daysRemaining;
    private int totalQuota;
    private int usedQuota;
    private int remainingQuota;

    // Campaign
    private int totalCampaigns;
    private int sentCampaigns;
    private int draftCampaigns;
    private int totalEmailsSent;
    private int totalEmailsFailed;

    // Contacts
    private int totalContacts;
    private int activeContacts;
    private int unsubscribedContacts;
    private int bouncedContacts;

   

    // SendLog
    private int totalSentLogs;
    private int totalSuccess;
    private int totalFailed;
    private int totalBounce;
}
