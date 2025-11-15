package com.example.sendflow.service;

import com.example.sendflow.dto.EmailVerifyDto;
import com.example.sendflow.entity.User;

public interface IVerifyEmail {
    void sendMail(EmailVerifyDto emailVerifyDto);

    // verify register
    void sendVerificationEmail(User user, String otp);

    // send otp to email forget-password
    void sendPasswordResetEmail(User user,String otp);

    // verify otp reset password
    boolean verifyResetOtp(String email, String otp);
    // verify otp register
    boolean verifyOtp(String email,String otp);
}
