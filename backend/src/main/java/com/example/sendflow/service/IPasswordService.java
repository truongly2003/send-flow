package com.example.sendflow.service;

import com.example.sendflow.dto.request.ResetPasswordRequest;
import com.example.sendflow.dto.request.UpdatePasswordRequest;

public interface IPasswordService {
    boolean changePassword(Long userId, UpdatePasswordRequest updatePasswordRequest);

    boolean forgetPassword(String email);

    boolean resetPassword(ResetPasswordRequest request);
}
