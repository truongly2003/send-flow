package com.example.sendflow.service;

import com.example.sendflow.entity.Subscription;
import com.example.sendflow.entity.Usage;

public interface IUsageService {
    Usage createUsage(Subscription subscription, String period);
}
