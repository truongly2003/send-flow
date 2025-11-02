package com.example.sendflow.controller;

import com.example.sendflow.service.IUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usage")
@RequiredArgsConstructor
public class UsageController {
    private final IUsageService usageService;
}
