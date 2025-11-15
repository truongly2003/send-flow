package com.example.sendflow.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}
