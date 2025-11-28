package com.example.sendflow.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class VnPayConfig {
    private final String tmnCode;
    private final String hashSecret;

    public VnPayConfig() {
        Dotenv dotenv = Dotenv.load(); // đọc file .env
        this.tmnCode = EnvConfig.get("VNPAY_TMN_CODE");
        this.hashSecret = EnvConfig.get("VNPAY_HASH_SECRET");
        // Validate bắt buộc
        if (tmnCode == null || tmnCode.isBlank()) {
            throw new IllegalStateException("VNPAY_TMN_CODE không được để trống!");
        }
        if (hashSecret == null || hashSecret.isBlank()) {
            throw new IllegalStateException("VNPAY_HASH_SECRET không được để trống!");
        }
    }

    public String getTmnCode() {
        return tmnCode;
    }

    public String getHashSecret() {
        return hashSecret;
    }
}
