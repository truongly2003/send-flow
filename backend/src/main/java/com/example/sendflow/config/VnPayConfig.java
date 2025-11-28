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
    }

    public String getTmnCode() {
        return tmnCode;
    }

    public String getHashSecret() {
        return hashSecret;
    }
}
