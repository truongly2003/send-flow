package com.example.sendflow.dto.response;

import com.example.sendflow.enums.Role;
import com.example.sendflow.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private String subscription;
    private SubscriptionStatus status;
    private Long totalCampaign;
    private Long totalEmailSend;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
