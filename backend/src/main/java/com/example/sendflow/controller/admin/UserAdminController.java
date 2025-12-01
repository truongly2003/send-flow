package com.example.sendflow.controller.admin;

import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.UserResponse;
import com.example.sendflow.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserAdminController {
    private final IUserService userService;
    // get all user
    @GetMapping("")
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
}
