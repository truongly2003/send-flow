package com.example.sendflow.controller;

import com.example.sendflow.dto.request.UserRequest;
import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.CampaignResponse;
import com.example.sendflow.dto.response.UserResponse;
import com.example.sendflow.service.IUserService;
import com.example.sendflow.service.IVerifyEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private  final IVerifyEmail verifyEmail;
    // register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> addUser(@RequestBody UserRequest userRequest) {
        // create user, create otp, send email
        Boolean addUser=userService.createUser(userRequest);
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                .code(2000)
                .message("Register success, please check verify your email")
                .data(addUser)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    // verify after register
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Boolean>> verifyOtp(@RequestParam String email,
                                                          @RequestParam String otp) {
        boolean verify=verifyEmail.verifyOtp(email,otp);
        if (verify) {
            ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                    .code(2000)
                    .message("Verify email successfully")
                    .data(true)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                    .code(4001)
                    .message("OTP invalid or expired")
                    .data(false)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    // get by userId
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long userId) {
        UserResponse userResponses=userService.getUserById(userId);
        ApiResponse<UserResponse> apiResponse=ApiResponse.<UserResponse>builder()
                .code(2000)
                .message("Success")
                .data(userResponses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    // get all user
    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUser() {
        List<UserResponse> userResponses=userService.getAllUsers();
        ApiResponse<List<UserResponse>> apiResponse=ApiResponse.<List<UserResponse>>builder()
                .code(2000)
                .message("Success")
                .data(userResponses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    // delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long userId) {
        Boolean addUser=userService.deleteUser(userId);
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                .code(2000)
                .message("Delete user successfully")
                .data(addUser)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    // update
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Boolean>> updateUser(@PathVariable Long userId,@RequestBody UserRequest userRequest) {
        Boolean addUser=userService.updateUser(userId,userRequest);
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                .code(2000)
                .message("Update user successfully")
                .data(addUser)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
