package com.example.sendflow.service.impl;


import com.example.sendflow.dto.request.ResetPasswordRequest;
import com.example.sendflow.dto.request.UpdatePasswordRequest;
import com.example.sendflow.entity.EmailVerification;
import com.example.sendflow.entity.User;
import com.example.sendflow.repository.EmailVerificationRepository;
import com.example.sendflow.repository.UserRepository;
import com.example.sendflow.service.IPasswordService;
import com.example.sendflow.service.IVerifyEmail;
import com.example.sendflow.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordService implements IPasswordService {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final IVerifyEmail verifyEmail;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    // change password
    @Override
    public boolean changePassword(Long userId, UpdatePasswordRequest request) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return false;
        }
        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
        return true;
    }

    // send otp forget-password
    @Override
    public boolean forgetPassword(String email) {
       User user = userRepository.findByEmail(email);
        if (user==null) {
            return false;
        }

        String otp = OtpUtil.generateOtp();
        EmailVerification verification = emailVerificationRepository
                .findByEmail(email)
                .orElse(new EmailVerification());
        verification.setEmail(email);
        verification.setOtp(otp);
        verification.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        emailVerificationRepository.save(verification);
        verifyEmail.sendPasswordResetEmail(user,otp);
        return true;
    }
    // verify otp and reset password
    @Override
    public boolean resetPassword(ResetPasswordRequest request) {
        if(!verifyEmail.verifyResetOtp(request.getEmail(),request.getOtp())){
            return false;
        }
        User user = userRepository.findByEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        emailVerificationRepository.deleteByEmail(request.getEmail());
        return true;
    }
}
