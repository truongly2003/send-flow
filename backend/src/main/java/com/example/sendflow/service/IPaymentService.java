package com.example.sendflow.service;

import com.example.sendflow.dto.request.PaymentRequest;
import com.example.sendflow.dto.response.PaymentResponse;

import java.util.Map;

public interface IPaymentService {
    // Tạo URL thanh toán
    PaymentResponse createPaymentUrl(PaymentRequest request);

    // Xử lý return URL từ VNPay (user-facing, trả JSON tạm)
    PaymentResponse handleReturnUrl(Map<String, String> params);

    // Xử lý IPN từ VNPay (server-to-server, update DB)
    String  handleIpn(Map<String, String> params);
}
