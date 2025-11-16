package com.example.sendflow.dto.response;

import com.example.sendflow.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private String planName;
    private Long userId;
    private String userName;
    private String email;
    private BigDecimal amount;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private String description;
    private LocalDateTime createdAt;
}
