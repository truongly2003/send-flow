package com.example.sendflow.controller;

import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.PagedResponse;
import com.example.sendflow.dto.response.SendLogResponse;
import com.example.sendflow.service.ISendLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sendlog")
@RequiredArgsConstructor
public class SendLogController {
    private final ISendLogService sendLogService;

    // lấy tất cả sendlog với campaignId
    @GetMapping("/{campaignId}")
    public ResponseEntity<ApiResponse<PagedResponse<SendLogResponse>>> getAllSendLogByCampaignId(
            @PathVariable Long campaignId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<SendLogResponse> sendLogResponses = sendLogService.getAllSendLogByCampaignId(campaignId, pageable);

        ApiResponse<PagedResponse<SendLogResponse>> response = ApiResponse.<PagedResponse<SendLogResponse>>builder()
                .code(2000)
                .message("Success")
                .data(sendLogResponses)
                .build();

        return ResponseEntity.ok(response);
    }
}
