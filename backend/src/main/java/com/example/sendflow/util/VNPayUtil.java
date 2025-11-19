package com.example.sendflow.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VNPayUtil {
    // truong2003@Tr

    // tạo chữ ký
    // Mục đích: VNPay dùng để verify tính toàn vẹn dữ liệu, chống giả mạo
    public static String hmacSHA512(final String key, final String data) {
        try {
            // Tạo instance Mac với algorithm HMacSHA512
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            // Chuyển key thành bytes
            byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
            // Tạo secret key spec
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            // Tính hash từ data
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xFF));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("HMAC-SHA512 error", e);
        }
    }
    // buildPaymentUrl: Xây dựng URL thanh toán đầy đủ cho VNPay
    // Mục đích: Sắp xếp params theo alphabet, encode, tính hash, ghép URL
    public static String buildPaymentUrl(Map<String, String> params, String secret, String baseUrl) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder hashData = new StringBuilder(); // để tính hash
        StringBuilder query = new StringBuilder(); // để build query string
        for (String key : keys) {
            String value = params.get(key);
            if (value != null && !value.isEmpty()) {
                try {
                    // Encode key và value cho URL safe
                    String encodedKey = URLEncoder.encode(key, StandardCharsets.US_ASCII);
                    String encodedValue = URLEncoder.encode(value, StandardCharsets.US_ASCII);
                    if (!hashData.isEmpty()) {
                        hashData.append('&');
                        query.append('&');
                    }
                    // Ghép vào hashData và query
                    hashData.append(encodedKey).append('=').append(encodedValue);
                    query.append(encodedKey).append('=').append(encodedValue);
                } catch (Exception e) {
                    throw new RuntimeException("URL encode error", e);
                }
            }
        }

        String queryString = query.toString();
        String secureHash = hmacSHA512(secret, hashData.toString());
        return baseUrl + "?" + queryString + "&vnp_SecureHash=" + secureHash; // Trả về URL đầy đủ để redirect user
    }
    // validateSignature: Kiểm tra chữ ký từ callback VNPay
    // Mục đích: Tính hash mới từ params (trừ hash cũ) và so sánh
    public static boolean validateSignature(Map<String, String> params, String receivedHash, String secret) {
        // Kiểm tra chữ ký từ callback của VNPay để tránh giả mạo
        String inputHash = params.remove("vnp_SecureHash");
        if (inputHash == null || !inputHash.equals(receivedHash)) {
            return false;
        }

        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder hashData = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            if (value != null && !value.isEmpty()) {
                try {
                    String encodedKey = URLEncoder.encode(key, StandardCharsets.US_ASCII);
                    String encodedValue = URLEncoder.encode(value, StandardCharsets.US_ASCII);
                    if (!hashData.isEmpty()) {
                        hashData.append('&');
                    }
                    hashData.append(encodedKey).append('=').append(encodedValue);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        String calculatedHash = hmacSHA512(secret, hashData.toString());// Tính hash mới
        return calculatedHash.equals(receivedHash); // True nếu chữ ký hợp lệ
    }
    // getCurrentTimeGMT7: Lấy thời gian hiện tại múi giờ VN (GMT+7)
    // Mục đích: VNPay yêu cầu định dạng yyyyMMddHHmmss
    public static String getCurrentTimeGMT7() {
        // Lấy thời gian hiện tại theo múi giờ GMT+7 (Việt Nam), định dạng yyyyMMddHHmmss
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }
    // getExpireTime: Tính thời gian hết hạn (15 phút sau createDate)
    // Mục đích: VNPay dùng để giới hạn thời gian giao dịch
    public static String getExpireTime(String createDate, int minutes) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime start = LocalDateTime.parse(createDate, fmt);
        LocalDateTime expire = start.plusMinutes(minutes);
        return expire.format(fmt);
    }
}
