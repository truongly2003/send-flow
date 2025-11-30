package com.example.sendflow.service.impl;

import com.example.sendflow.entity.Subscription;
import com.example.sendflow.entity.Usage;
import com.example.sendflow.repository.UsageRepository;
import com.example.sendflow.service.IUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsageService implements IUsageService {
    private final UsageRepository usageRepository;
    @Override
    public Usage createUsage(Subscription subscription, String period) {
        Usage usage = new Usage();
        usage.setSubscription(subscription);
        usage.setPeriod(period);
        usage.setEmailCount(0);
        usage.setContactCount(0);
        usage.setTemplateCount(0);
        usage.setCampaignCount(0);
        usage.setCreatedAt(LocalDateTime.now());
        usage.setUpdatedAt(LocalDateTime.now());
        return usageRepository.save(usage);
    }
}
