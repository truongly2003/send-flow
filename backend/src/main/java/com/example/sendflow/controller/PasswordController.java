package com.example.sendflow.controller;

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
    public ResponseEntity<?> forgetPassword(@RequestParam String email) {
        boolean forget = passwordService.forgetPassword(email);
        return forget ? ResponseEntity.ok("OTP send!") :
                ResponseEntity.badRequest().body("Email is correct");
    }
    // reset password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email,@RequestParam String newPassword,@RequestParam String otp) {
        boolean reset=passwordService.resetPassword(email,newPassword,otp);
        return reset ? ResponseEntity.ok("Reset password successfully") : ResponseEntity.badRequest().body("Reset password failed");
    }

    // check otp
    @PostMapping("/verify-check-otp")
    public ResponseEntity<?> verifyCheckOtp(@RequestParam String otp) {
        return ResponseEntity.ok("Verify OTP successfully");
    }
}
