package com.example.sendflow.controller.admin;

import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.DashboardAdminResponse;
import com.example.sendflow.service.IDashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class DashBoardAdminController {
    private final IDashBoardService dashboardService;
    // admin
    @GetMapping("")
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

    // revenue
    @GetMapping("/revenue-by-plan")
    public ResponseEntity<?> getRevenue(
            @RequestParam int year,
            @RequestParam int month) {

        return ResponseEntity.ok(
                dashboardService.getAllRevenue(year, month)
        );
    }
}
