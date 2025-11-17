package com.example.sendflow.controller;

import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.DashboardAdminResponse;
import com.example.sendflow.dto.response.DashboardUserResponse;
import com.example.sendflow.service.IDashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final IDashBoardService dashboardService;

    // user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<DashboardUserResponse>> getUserDashboard(
            @PathVariable Long userId) {

        DashboardUserResponse data = dashboardService.getAllDashBoardUser(userId);

        return ResponseEntity.ok(
                ApiResponse.<DashboardUserResponse>builder()
                        .code(2000)
                        .message("success")
                        .data(data)
                        .build()
        );
    }

    // admin
    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<DashboardAdminResponse>> getAdminDashboard() {
        DashboardAdminResponse data = dashboardService.getAllDashBoardAdmin();
        return ResponseEntity.ok(
                ApiResponse.<DashboardAdminResponse>builder()
                        .code(2000)
                        .message("success")
                        .data(data)
                        .build()
        );
    }
}
