package com.example.sendflow.controller;

import com.example.sendflow.dto.request.ResetPasswordRequest;
import com.example.sendflow.dto.request.UpdatePasswordRequest;
import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.service.IPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {
    private final IPasswordService passwordService;

    // change password
    @PutMapping("/change-password")
    ResponseEntity<ApiResponse<Boolean>> changePassword(@RequestParam Long userId, @RequestBody UpdatePasswordRequest request) {
        try {
            boolean update = passwordService.changePassword(userId, request);
            if (update) {
                return ResponseEntity.ok(new ApiResponse<>(200, "Change password successfully", true));

            } else {
                return ResponseEntity.ok(new ApiResponse<>(201, "Change password failed", false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, e.getMessage(), false));
        }
    }
    // send otp to email forget-password
    @PostMapping("/forget-password")
    public ResponseEntity<ApiResponse<Boolean>> forgetPassword(@RequestParam String email) {
        boolean success = passwordService.forgetPassword(email);
        int code = success ? 2000 : 2001;
        String message = success ? "OTP sent successfully" : "Failed to send OTP";
        return ResponseEntity.ok(new ApiResponse<>(code, message, success));
    }

    // reset password
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Boolean>> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean success = passwordService.resetPassword(request);
        int code = success ? 2000 : 2001;
        String message = success ? "Reset password successfully" : "Reset password failed";
        return ResponseEntity.ok(new ApiResponse<>(code, message, success));
    }



}
