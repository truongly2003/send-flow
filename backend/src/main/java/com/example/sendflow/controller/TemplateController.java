package com.example.sendflow.controller;

import com.example.sendflow.service.ITemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/template")
@RequiredArgsConstructor
public class TemplateController {
    private final ITemplateService templateService;
}
