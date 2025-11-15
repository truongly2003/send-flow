package com.example.sendflow.controller;

import com.example.sendflow.dto.request.CampaignRequest;
import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.CampaignResponse;
import com.example.sendflow.service.ICampaignService;
import com.example.sendflow.service.impl.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaign")
@RequiredArgsConstructor
public class CampaignController {
    private final ICampaignService campaignService;
    // Lấy tất cả các chiến dịch
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<CampaignResponse>>> getAllCampaigns(@PathVariable Long userId) {
        List<CampaignResponse> campaigns = campaignService.getAllCampaigns(userId);
        ApiResponse<List<CampaignResponse>> apiResponse = ApiResponse.<List<CampaignResponse>>builder()
                .code(2000)
                .message("Success")
                .data(campaigns)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    // Tạo chiến dịch mới
    @PostMapping
    ResponseEntity<ApiResponse<Void>> createCampaign(@RequestBody CampaignRequest campaignRequest) {
         campaignService.createCampaign(campaignRequest);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(2000)
                .message("Create campaign success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    // Cập nhật chiến dịch
    @PutMapping("/{campaignId}")
    ResponseEntity<ApiResponse<Void>> updateCampaign(@PathVariable Long campaignId,@RequestBody CampaignRequest campaignRequest) {
        campaignService.updateCampaign(campaignId,campaignRequest);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(2000)
                .message("Update campaign success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    // Xóa chiến dịch
    @DeleteMapping("/{campaignId}")
    ResponseEntity<ApiResponse<Void>> deleteCampaign(@PathVariable Long campaignId) {
        campaignService.deleteCampaign(campaignId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(2000)
                .message("Delete campaign success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    // Gửi mail
    @PostMapping("/{id}/send")
    public ResponseEntity<ApiResponse<String>> sendCampaign(@PathVariable Long id) {
        campaignService.sendCampaignMail(id);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .code(2000)
                .message("Campaign sending started!")
                .data(null)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
