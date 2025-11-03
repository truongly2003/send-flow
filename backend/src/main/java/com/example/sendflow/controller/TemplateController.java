package com.example.sendflow.controller;

import com.example.sendflow.dto.request.TemplateRequest;
import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.TemplateResponse;
import com.example.sendflow.service.ITemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
@RequiredArgsConstructor
public class TemplateController {
    private final ITemplateService templateService;


    // Lấy tất cả
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<TemplateResponse>>> getAllTemplates(@PathVariable Long userId) {
        List<TemplateResponse> templateResponses = templateService.getTemplates(userId);
        ApiResponse<List<TemplateResponse>> apiResponse = ApiResponse.<List<TemplateResponse>>builder()
                .code(2000)
                .message("success")
                .data(templateResponses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    // Tạo mới template
    @PostMapping
    public ResponseEntity<ApiResponse<TemplateResponse>> createTemplate(@Valid @RequestBody TemplateRequest templateRequest) {
        TemplateResponse templateResponses = templateService.createTemplate(templateRequest);
        ApiResponse<TemplateResponse> response = ApiResponse.<TemplateResponse>builder()
                .code(2000)
                .message("Create template success")
                .data(templateResponses)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Lấy template theo ID
    @GetMapping("/{templateId}")
    public ResponseEntity<ApiResponse<TemplateResponse>> getTemplateById(@PathVariable Long templateId) {
        TemplateResponse templateResponses = templateService.getTemplate(templateId);
        ApiResponse<TemplateResponse> response = ApiResponse.<TemplateResponse>builder()
                .code(2000)
                .message("success")
                .data(templateResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    // Cập nhật template
    @PutMapping("/{templateId}")
    public ResponseEntity<ApiResponse<TemplateResponse>> updatePlan(@PathVariable Long templateId, @RequestBody TemplateRequest templateRequest) {
        TemplateResponse templateResponses = templateService.updateTemplate(templateId, templateRequest);
        ApiResponse<TemplateResponse> response = ApiResponse.<TemplateResponse>builder()
                .code(2000)
                .message("Update template success")
                .data(templateResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    // Xóa template
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePlan(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(2000)
                .message("Delete template success")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }
}
