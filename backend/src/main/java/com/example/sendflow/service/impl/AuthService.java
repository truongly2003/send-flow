package com.example.sendflow.service.impl;

import com.example.sendflow.config.JwtConfig;
import com.example.sendflow.dto.request.AuthRequest;
import com.example.sendflow.dto.response.AuthResponse;
import com.example.sendflow.entity.User;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.repository.UserRepository;
import com.example.sendflow.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService implements IAuthService {
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // login
    @Override
    public AuthResponse login(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        boolean matches = bCryptPasswordEncoder.matches(authRequest.getPassword(), user.getPassword());
        if (!matches) {
            throw new BadCredentialsException("Password is incorrect!");
        }
        String accessToken = jwtConfig.generateAccessToken(authRequest.getEmail(), authRequest.getPassword());
        String refreshToken = jwtConfig.generateRefreshToken(authRequest.getEmail(), authRequest.getPassword());
        return AuthResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRole())
                .build();
    }

    // verify token
    @Override
    public boolean authenticate(String token) {
        return jwtConfig.validateToken(token);
    }
}
