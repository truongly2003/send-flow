package com.example.sendflow.service.impl;

import com.example.sendflow.dto.EmailVerifyDto;
import com.example.sendflow.entity.EmailVerification;
import com.example.sendflow.entity.Transaction;
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
import java.time.format.DateTimeFormatter;

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
        if (emailVerification == null) return false;
        // check expiryDate
        if (emailVerification.getExpiryDate().isBefore(LocalDateTime.now())) {
            emailVerificationRepository.delete(emailVerification);
            return false;
        }
        if (!emailVerification.getOtp().equals(otp)) return false;
        emailVerificationRepository.delete(emailVerification);
        return true;
    }

    // verify otp register
    @Override
    public boolean verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;
        EmailVerification emailVerification = emailVerificationRepository
                .findByEmail(email)
                .orElse(null);
        if (emailVerification == null) return false;
        // check expiryDate
        if (emailVerification.getExpiryDate().isBefore(LocalDateTime.now())) {
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

    // verify invoice
    @Override
    public void sendInvoiceEmail(User user, Transaction transaction) {
        StringBuilder sb = new StringBuilder(512);
        sb.append("<html>");
        sb.append("<body style='font-family: Arial, sans-serif; line-height: 1.6;'>");
        sb.append("<h2 style='color:#4CAF50;'>Hóa đơn thanh toán thành công</h2>");
        sb.append("<p>Xin chào <b>")
                .append(user.getName())
                .append("</b>,</p>");
        sb.append("<p>Bạn đã thanh toán thành công gói: <b>")
                .append(transaction.getPlan().getName())
                .append("</b></p>");
        sb.append("<p>Số tiền: <b>")
                .append(transaction.getAmount())
                .append(" VND</b></p>");
        sb.append("<p>Mã giao dịch: <b>")
                .append(transaction.getReference())
                .append("</b></p>");
        sb.append("<p>Thời gian: <b>")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .append("</b></p>");
        sb.append("<br/><p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>");
        sb.append("<hr/><p style='font-size:12px;color:#888;'>LyTruong - Send-Flow SaaS</p>");
        sb.append("</body>");
        sb.append("</html>");
        String subject = "Thanh toán hóa đơn #" + transaction.getReference();
        sendHtmlEmail(user.getEmail(), subject, sb.toString());
    }

}
