package com.example.sendflow.service.impl;

import com.example.sendflow.config.VnPayConfig;
import com.example.sendflow.dto.request.PaymentRequest;
import com.example.sendflow.dto.response.PaymentResponse;
import com.example.sendflow.dto.response.TransactionResponse;
import com.example.sendflow.entity.Plan;
import com.example.sendflow.entity.Subscription;
import com.example.sendflow.entity.Transaction;
import com.example.sendflow.entity.User;
import com.example.sendflow.enums.PaymentStatus;
import com.example.sendflow.enums.SubscriptionStatus;
import com.example.sendflow.repository.PlanRepository;
import com.example.sendflow.repository.SubscriptionRepository;
import com.example.sendflow.repository.TransactionRepository;
import com.example.sendflow.repository.UserRepository;
import com.example.sendflow.service.IPaymentService;
import com.example.sendflow.util.VNPayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final VnPayConfig vnPayConfig;
    //    @Value("${vnpay.tmnCode}")
//    private final String vnp_TmnCode = vnPayConfig.getTmnCode();

    //    @Value("${vnpay.hashSecret}")
//    private final String vnp_HashSecret = vnPayConfig.getHashSecret();

    //    @Value("${vnpay.returnUrl}")
    private String vnp_ReturnUrl = "http://localhost:8080/send-flow/api/payment/vnpay/return";
    // Trong PaymentService
    private String vnp_IpnUrl = "https://new-spiders-admire.loca.lt/send-flow/api/payment/vnpay/ipn";
    //    @Value("${vnpay.baseUrl}")
    private String vnp_BaseUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    private final TransactionRepository transactionRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    // Logic: Tìm user/plan → Tạo txnRef unique -> Save PENDING -> Build params → Return URL
    @Override
    public PaymentResponse createPaymentUrl(PaymentRequest request) {
        try {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));
            Plan plan = planRepository.findById(request.getPlanId())
                    .orElseThrow(() -> new RuntimeException("Plan not found: " + request.getPlanId()));
            BigDecimal amount = request.getAmount() != null ? request.getAmount() : plan.getPrice();
            // Check amount > 0
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Invalid amount: " + amount);
            }

            // 1) build and save transaction PENDING
            Transaction transaction = Transaction.builder()
                    .user(user)
                    .plan(plan)
                    .amount(amount)
                    .paymentMethod(request.getPaymentMethod())
                    .paymentStatus(PaymentStatus.PENDING)
                    .build();
            transaction = transactionRepository.save(transaction);

            // 2) Set txnRef = "TXN_{id}" and update
            String txnRef = "TXN_" + transaction.getId();
            transaction.setReference(txnRef);
            transactionRepository.save(transaction);

            // 3) build vnp params
            String createDate = VNPayUtil.getCurrentTimeGMT7();
            String expireDate = VNPayUtil.getExpireTime(createDate, 15);

            Map<String, String> params = new HashMap<>();
            params.put("vnp_Version", "2.1.0");  // Phiên bản API
            params.put("vnp_Command", "pay");    // Lệnh thanh toán
            params.put("vnp_TmnCode", vnPayConfig.getTmnCode());  // Merchant code
            params.put("vnp_Amount", amount.multiply(new BigDecimal(100)).toString());  // *100
            params.put("vnp_CreateDate", createDate);
            params.put("vnp_CurrCode", "VND");   // Tiền tệ
            params.put("vnp_IpAddr", "127.0.0.1");  // IP user (cập nhật từ request nếu cần)
            params.put("vnp_Locale", "vn");      // Ngôn ngữ VN
            params.put("vnp_OrderInfo", "Thanh toan goi " + plan.getName() + " cho nguoi dung " + user.getName());  // Mô tả
            params.put("vnp_OrderType", "subscription");  // Loại đơn: subscription
            params.put("vnp_ReturnUrl", vnp_ReturnUrl);  // URL callback
            params.put("vnp_TxnRef", txnRef);    // Mã ref
            params.put("vnp_ExpireDate", expireDate);  // Hết hạn 15p

            // 4) Build full URL với hash
            String paymentUrl = VNPayUtil.buildPaymentUrl(params, vnPayConfig.getHashSecret(), vnp_BaseUrl);
            // Trả response success
            PaymentResponse response = new PaymentResponse();
            response.setSuccess(true);
            response.setMessage("Tạo URL thành công");
            response.setPaymentUrl(paymentUrl);
            response.setTransactionId(transaction.getId());
            response.setOrderInfo(params.get("vnp_OrderInfo"));
            return response;

        } catch (Exception e) {
            PaymentResponse response = new PaymentResponse();
            response.setSuccess(false);
            response.setMessage("Lỗi tạo URL: " + e.getMessage());
            response.setError(e.getMessage());
            return response;
        }
    }

    // handleReturnUrl: Xử lý callback return từ VNPay
    // Logic: Validate hash → Parse code → Trả JSON tạm (no update DB, chờ IPN)
    @Override
    public PaymentResponse handleReturnUrl(Map<String, String> params) {
        PaymentResponse response = new PaymentResponse();
        // Lấy và validate hash
        String secureHash = params.get("vnp_SecureHash");
        if (!VNPayUtil.validateSignature(params, secureHash, vnPayConfig.getHashSecret())) {
            response.setSuccess(false);
            response.setMessage("Chữ ký không hợp lệ");
            response.setError("Invalid signature");
            return response;
        }
        // Lấy responseCode từ VNPay (00 = success)
        String responseCode = params.get("vnp_ResponseCode");
        String txnRef = params.get("vnp_TxnRef");

        Transaction transaction = transactionRepository.findByReference(txnRef)
                .orElse(null);

        if (transaction == null) {
            response.setSuccess(false);
            response.setMessage("Transaction not found");
            return response;
        }

        response.setTransactionId(transaction.getId());
        response.setResponseCode(responseCode);

        if ("00".equals(responseCode)) {
            response.setSuccess(true);
            response.setMessage("Thanh toán thành công! Chờ xác nhận từ IPN.");
        } else {
            response.setSuccess(false);
            response.setMessage("Thanh toán thất bại");
            response.setError(params.get("vnp_ResponseMessage"));
        }
        return response;
    }

    // handleIpn: Xử lý IPN (server-to-server) từ VNPay
    // Logic: Validate nghiêm ngặt → Update status → Tạo Subscription nếu success
    @Override
    public String handleIpn(Map<String, String> params) {
        try {
            // 1. Validate checksum
            if (!VNPayUtil.validateSignature(params, params.get("vnp_SecureHash"), vnPayConfig.getHashSecret())) {
                return "Invalid signature";
            }
            // get data to params
            String txnRef = params.get("vnp_TxnRef");
            String responseCode = params.get("vnp_ResponseCode");
            // Tìm transaction bằng reference
            Transaction transaction = transactionRepository.findByReference(txnRef).orElse(null);
            if (transaction == null) return "Transaction not found";
            // 2. Check trùng IPN
            if (transaction.getPaymentStatus() == PaymentStatus.SUCCESS) return "Already completed";
            if ("00".equals(responseCode)) {
                // set status
                transaction.setPaymentStatus(PaymentStatus.SUCCESS);
                // active subscription
                Subscription subscription = Subscription.builder()
                        .user(transaction.getUser())
                        .plan(transaction.getPlan())
                        .startTime(LocalDateTime.now())  // Bắt đầu ngay
                        .endTime(LocalDateTime.now().plusMonths(transaction.getPlan().getPeriod().ordinal()))  // + tháng từ plan
                        .status(SubscriptionStatus.ACTIVE)
                        .build();
                subscriptionRepository.save(subscription);
                transactionRepository.save(transaction);
                return "00";
            } else {
                transaction.setPaymentStatus(PaymentStatus.FAILED);
                transactionRepository.save(transaction);
                return "02";
            }

        } catch (Exception e) {
            return "99";
        }
    }
}
