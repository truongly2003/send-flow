package com.example.sendflow.controller;

import com.example.sendflow.service.ISendLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendlog")
@RequiredArgsConstructor
public class SendLogController {
    private final ISendLogService sendLogService;
}
