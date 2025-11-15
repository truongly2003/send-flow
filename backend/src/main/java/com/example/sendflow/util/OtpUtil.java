package com.example.sendflow.util;

import java.security.SecureRandom;

public class OtpUtil {
    private static  final SecureRandom random = new SecureRandom();
    public static String generateOtp() {
        int otp = random.nextInt(900000) + 100000;
        return String.valueOf(otp);
    }
}
