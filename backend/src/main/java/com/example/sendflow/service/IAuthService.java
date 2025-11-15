package com.example.sendflow.service;

import com.example.sendflow.dto.request.AuthRequest;
import com.example.sendflow.dto.response.AuthResponse;

public interface IAuthService {
    // login
    AuthResponse login(AuthRequest authRequest);
    // verify token
    boolean authenticate(String token);
}
