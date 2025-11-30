package com.example.sendflow.controller;

import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.SubscriptionResponse;
import com.example.sendflow.service.ISubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;
    @GetMapping("{userId}")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> getCurrentSubscription(@PathVariable Long userId){
        SubscriptionResponse subscriptionResponse=subscriptionService.getCurrentSubscriptionByUserId(userId);
        ApiResponse<SubscriptionResponse> apiResponse=ApiResponse.<SubscriptionResponse>builder()
                .message("Subscription found")
                .data(subscriptionResponse)
                .code(2000)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
