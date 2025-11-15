package com.example.sendflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private boolean status;
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String name;
    private String email;
}
