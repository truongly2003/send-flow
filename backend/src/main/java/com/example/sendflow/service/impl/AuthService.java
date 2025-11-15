package com.example.sendflow.service.impl;

import com.example.sendflow.dto.request.AuthRequest;
import com.example.sendflow.dto.response.AuthResponse;
import com.example.sendflow.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService implements IAuthService {
    // login
    @Override
    public AuthResponse login(AuthRequest authRequest) {
        return null;
    }
    // verify token
    @Override
    public boolean authenticate(String token) {
        return false;
    }
}
