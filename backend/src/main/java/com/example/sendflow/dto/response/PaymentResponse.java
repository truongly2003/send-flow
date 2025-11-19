package com.example.sendflow.dto.response;

import com.example.sendflow.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private boolean success;
    private String message;
    private String paymentUrl; // Cho initiate
    private Long transactionId; // Cho return/IPN
    private String orderInfo; // Optional
    private PaymentStatus status;
    private String responseCode; // Cho return
    private String error;
}
