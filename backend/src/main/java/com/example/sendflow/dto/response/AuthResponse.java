package com.example.sendflow.dto.response;

import com.example.sendflow.enums.Role;
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
    private Long userId;
    private String name;
    private String email;
    private Role role;
}
