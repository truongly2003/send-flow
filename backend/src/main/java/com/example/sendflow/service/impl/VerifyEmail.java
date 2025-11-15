package com.example.sendflow.service.impl;

import com.example.sendflow.dto.EmailVerifyDto;
import com.example.sendflow.entity.EmailVerification;
import com.example.sendflow.entity.User;
import com.example.sendflow.repository.EmailVerificationRepository;
import com.example.sendflow.repository.UserRepository;
import com.example.sendflow.service.IVerifyEmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VerifyEmail implements IVerifyEmail {

    private final EmailVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    public VerifyEmail(EmailVerificationRepository emailVerificationRepository, UserRepository userRepository, JavaMailSender mailSender) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("truonglykhong@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void sendMail(EmailVerifyDto emailVerifyDto) {
        sendHtmlEmail(
                emailVerifyDto.getToEmail(),
                emailVerifyDto.getSubject(),
                emailVerifyDto.getBody()
        );
    }

    // send otp to email forget-password
    @Override
    public void sendPasswordResetEmail(User user, String otp) {
        String subject = "Your Password Reset OTP";

        String body = """
                <h2>Hi %s!</h2>
                <p>Your have request to reset your password.</p>
                <p>Your OTP code is:</p>
                <h1 style='color:blue;'>%s</h1>
                <p>This OTP is valid for minutes.</p>
                """.formatted(user.getName(), otp);
        sendHtmlEmail(user.getEmail(), subject, body);
    }

    // verify email register
    @Override
    public void sendVerificationEmail(User user, String otp) {
        String subject = "Verify Your Account";
        String body = """
                <h2>Hi %s!</h2>
                <p>Thank you for register. please verify your email by using the OTP below:</p>
                <p>Your OTP code is:</p>
                <h1 style='color:blue;'>%s</h1>
                <p>This OTP is valid for minutes.</p>
                """.formatted(user.getName(), otp);
        sendHtmlEmail(user.getEmail(), subject, body);
    }

    // verify otp reset password
    @Override
    public boolean verifyResetOtp(String email, String otp) {
        EmailVerification emailVerification = emailVerificationRepository
                .findByEmail(email)
                .orElse(null);
        if(emailVerification == null) return false;
        // check expiryDate
        if(emailVerification.getExpiryDate().isBefore(LocalDateTime.now())) {
            emailVerificationRepository.delete(emailVerification);
            return false;
        }
        if (!emailVerification.getOtp().equals(otp)) return false;
        emailVerificationRepository.delete(emailVerification);
        return true;
    }
    // verify otp
    @Override
    public boolean verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email);
        if(user == null) return false;
        EmailVerification emailVerification = emailVerificationRepository
                .findByEmail(email)
                .orElse(null);
        if(emailVerification == null) return false;
        // check expiryDate
        if(emailVerification.getExpiryDate().isBefore(LocalDateTime.now())) {
            emailVerificationRepository.delete(emailVerification);
            return false;
        }
        if (!emailVerification.getOtp().equals(otp)) return false;
        // set active user = true
        user.setActive(true);
        userRepository.save(user);

        emailVerificationRepository.delete(emailVerification);
        return true;
    }

}
