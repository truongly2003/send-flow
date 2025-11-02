package com.example.sendflow.service.impl;

import com.example.sendflow.dto.response.SubscriptionResponse;
import com.example.sendflow.entity.Subscription;
import com.example.sendflow.enums.SubscriptionStatus;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.mapper.SubscriptionMapper;
import com.example.sendflow.repository.SubscriptionRepository;
import com.example.sendflow.service.ISubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public SubscriptionResponse getCurrentSubscriptionByUserId(Long userId) {
        Subscription subscription = subscriptionRepository.findCurrentByUserId(userId, SubscriptionStatus.ACTIVE, LocalDateTime.now()).orElseThrow(
                () -> new ResourceNotFoundException("Subscription not found")
        );
        SubscriptionResponse subscriptionResponse = subscriptionMapper.toSubscriptionResponse(subscription);
        subscriptionResponse.setPlanName(subscription.getPlan().getName());
        subscriptionResponse.setUserId(userId);
        subscriptionResponse.setPlanId(subscription.getPlan().getId());
        return subscriptionResponse;
    }
}
