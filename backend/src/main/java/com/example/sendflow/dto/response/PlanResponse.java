package com.example.sendflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponse {
    private Long id;

    private String name;
    private String description;

    private BigDecimal price;
    private String currency;

    private Integer maxContacts;
    private Integer maxEmailsPerMonth;
    private Integer maxCampaignsPerMonth;
    private Integer maxTemplates;

    private Boolean allowSmtpCustom;
    private Integer allowTeamMembers;

    private String status;
    private String period;
}
