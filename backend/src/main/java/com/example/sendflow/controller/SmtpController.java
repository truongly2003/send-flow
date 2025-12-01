package com.example.sendflow.controller;

import com.example.sendflow.dto.SmtpConfigDto;
import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.entity.SmtpConfig;
import com.example.sendflow.service.ISmtpConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/smtp")
@RequiredArgsConstructor
public class SmtpController {
    private final ISmtpConfigService smtpConfigService;
    @PostMapping("/save")
    public ResponseEntity<?> saveConfig(@RequestBody SmtpConfigDto req) {
        SmtpConfigDto saved = smtpConfigService.saveSmtpConfig(req);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/test")
    public ResponseEntity<?> testConnection(@RequestBody SmtpConfigDto request) {
        try {
            smtpConfigService.testConnection(request);
            return ResponseEntity.ok(Map.of("success", true, "message", "Kết nối thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", e.getMessage())
            );
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<SmtpConfigDto>> getSmtpConfig(@PathVariable Long userId) {
        ApiResponse<SmtpConfigDto> apiResponse=ApiResponse.<SmtpConfigDto>builder()
                .code(2000)
                .message("OK")
                .data(smtpConfigService.getSmtpConfig(userId))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
