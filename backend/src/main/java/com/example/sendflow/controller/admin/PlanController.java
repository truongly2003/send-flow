package com.example.sendflow.controller.admin;

import com.example.sendflow.dto.request.PlanRequest;
import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.PlanResponse;
import com.example.sendflow.service.IPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/plan")
@RequiredArgsConstructor
public class PlanController {
    private final IPlanService planService;

    // Lấy tất cả
    @GetMapping
    public ResponseEntity<ApiResponse<List<PlanResponse>>> getAllPlans() {
        List<PlanResponse> planResponses = planService.getAllPlan();
        ApiResponse<List<PlanResponse>> apiResponse = ApiResponse.<List<PlanResponse>>builder()
                .code(2000)
                .message("success")
                .data(planResponses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    // Tạo mới plan
    @PostMapping
    public ResponseEntity<ApiResponse<PlanResponse>> createPlan(@Valid @RequestBody PlanRequest planRequest) {
        PlanResponse planResponse = planService.createPlan(planRequest);
        ApiResponse<PlanResponse> response = ApiResponse.<PlanResponse>builder()
                .code(2000)
                .message("Create plan success")
                .data(planResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Lấy plan theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlanResponse>> getPlanById(@PathVariable Long id) {
        PlanResponse planResponse = planService.getPlanById(id);
        ApiResponse<PlanResponse> response = ApiResponse.<PlanResponse>builder()
                .code(2000)
                .message("success")
                .data(planResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    // Cập nhật plan
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PlanResponse>> updatePlan(@PathVariable Long id, @RequestBody PlanRequest planRequest) {
        PlanResponse planResponse = planService.updatePlan(id, planRequest);
        ApiResponse<PlanResponse> response = ApiResponse.<PlanResponse>builder()
                .code(2000)
                .message("Update plan success")
                .data(planResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    // Xóa plan
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(2000)
                .message("Delete plan success")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }
}
