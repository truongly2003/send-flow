package com.example.sendflow.controller;

import com.example.sendflow.service.ICampaignService;
import com.example.sendflow.service.impl.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign")
@RequiredArgsConstructor
public class CampaignController {
    private final ICampaignService campaignService;
}
