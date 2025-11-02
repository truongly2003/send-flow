package com.example.sendflow.service;

import com.example.sendflow.dto.response.SubscriptionResponse;

public interface ISubscriptionService {
    SubscriptionResponse getCurrentSubscriptionByUserId(Long userId);
}
