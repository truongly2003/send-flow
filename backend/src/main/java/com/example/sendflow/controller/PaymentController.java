package com.example.sendflow.controller;

import com.example.sendflow.dto.request.PaymentRequest;
import com.example.sendflow.dto.response.PaymentResponse;
import com.example.sendflow.entity.Transaction;
import com.example.sendflow.repository.TransactionRepository;
import com.example.sendflow.service.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;
    private final TransactionRepository transactionRepository;
    // crete url
    @PostMapping("/vnpay/create-url")
    public ResponseEntity<PaymentResponse> initiatePayment(
            @RequestBody PaymentRequest request,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(paymentService.createPaymentUrl(request));
    }

    // VNPay redirect user về đây sau khi thanh toán
    @GetMapping("/vnpay/return")
    public ResponseEntity<PaymentResponse> handleReturn(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            params.put(key, request.getParameter(key));
        }
        PaymentResponse response = paymentService.handleReturnUrl(params);
        String redirectUrl = "http://localhost:5173/payment-processing?transactionId=" + response.getTransactionId();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }

    // POST /vnpay/ipn: IPN từ VNPay (server-to-server)
    @GetMapping("/vnpay/ipn")  // VNPay dùng POST
    public ResponseEntity<String> handleIpn(HttpServletRequest request) {
        Map<String, String> params =new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            params.put(key, request.getParameter(key));
        }
        return ResponseEntity.ok(paymentService.handleIpn(params));
    }

    @GetMapping("/transaction/{id}/status")
    public ResponseEntity<?> getTransactionStatus(@PathVariable Long id) {
        Transaction t = transactionRepository.findById(id).orElse(null);
        if (t == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of(
                "status", t.getPaymentStatus().name()
        ));
    }

}
