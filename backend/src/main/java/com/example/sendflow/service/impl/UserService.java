package com.example.sendflow.service.impl;

import com.example.sendflow.dto.request.UserRequest;
import com.example.sendflow.dto.response.UserResponse;
import com.example.sendflow.entity.EmailVerification;
import com.example.sendflow.entity.Subscription;
import com.example.sendflow.entity.User;
import com.example.sendflow.enums.Role;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.mapper.UserMapper;
import com.example.sendflow.repository.EmailVerificationRepository;
import com.example.sendflow.repository.UsageRepository;
import com.example.sendflow.repository.UserRepository;
import com.example.sendflow.service.IUserService;
import com.example.sendflow.service.IVerifyEmail;
import com.example.sendflow.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UsageRepository usageRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final IVerifyEmail verifyEmail;
    private final UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ResourceNotFoundException("Email is already in use");
        }
        User user = userMapper.toUser(userRequest);
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        String otp= OtpUtil.generateOtp();
        EmailVerification emailVerification=new EmailVerification();
        emailVerification.setEmail(userRequest.getEmail());
        emailVerification.setOtp(otp);
        emailVerification.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        emailVerificationRepository.save(emailVerification);
        verifyEmail.sendVerificationEmail(user,otp);
        return true;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));

        Subscription lastSubscription = user.getSubscriptions() != null && !user.getSubscriptions().isEmpty()
                ? user.getSubscriptions().get(user.getSubscriptions().size() - 1) : null;

        Long totalCampaign = (long) (user.getCampaigns() != null ? user.getCampaigns().size() : 0);

        Long totalMail = usageRepository.getTotalEmailByUser(userId);
        assert lastSubscription != null;
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
                .totalCampaign(totalCampaign)
                .totalEmailSend(totalMail)
                .isActive(user.isActive())
                .subscriptionStatus(lastSubscription.getStatus())
                .subscription(lastSubscription.getPlan().getName())
                .createdAt(lastSubscription.getCreatedAt())
                .build();
    }

    @Override
    public boolean updateUser(Long userId, UserRequest userRequest) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userMapper.updateUserFromRequest(userRequest, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return true;
    }

    @Override
    public boolean deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(existingUser);
        return true;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(
                user -> {
                    Subscription lastSubscription = user.getSubscriptions() != null && !user.getSubscriptions().isEmpty()
                            ? user.getSubscriptions().get(user.getSubscriptions().size() - 1) : null;
                    Long totalCampaign = (long) (user.getCampaigns() != null ? user.getCampaigns().size() : 0);
                    Long totalMail = usageRepository.getTotalEmailByUser(user.getId());
                    return UserResponse.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .name(user.getName())
                            .phone(user.getPhone())
                            .address(user.getAddress())
                            .role(user.getRole())
                            .lastLogin(user.getLastLogin())
                            .totalCampaign(totalCampaign)
                            .totalEmailSend(totalMail)
                            .isActive(user.isActive())
                            .subscriptionStatus(lastSubscription != null ? lastSubscription.getStatus() : null)
                            .subscription(lastSubscription != null ? lastSubscription.getPlan().getName() : null)
                            .createdAt(lastSubscription != null ? lastSubscription.getCreatedAt() : null)
                            .build();
                }
        ).toList();
    }
}
