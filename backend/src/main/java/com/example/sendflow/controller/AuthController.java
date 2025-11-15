package com.example.sendflow.controller;

import com.example.sendflow.dto.request.AuthRequest;
import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.AuthResponse;
import com.example.sendflow.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;
    // login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.login(authRequest);
        ApiResponse<AuthResponse> apiResponse=ApiResponse.<AuthResponse>builder()
                .code(2000)
                .message("Login successfully")
                .data(authResponse).build();
        return ResponseEntity.ok(apiResponse);
    }
    // verify token
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<Boolean>> authenticate(@RequestParam String token) {
        boolean authenticated = authService.authenticate(token);
        ApiResponse<Boolean> apiResponse=ApiResponse.<Boolean>builder()
                .code(authenticated ? 2000 : 4001)
                .message(authenticated ? "Authentication successful" : "Authentication failed")
                .data(authenticated)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
