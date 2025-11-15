package com.example.sendflow.service;

import com.example.sendflow.dto.request.UserRequest;
import com.example.sendflow.dto.response.UserResponse;

import java.util.List;

public interface IUserService {
    // register
    boolean createUser(UserRequest userRequest);

    UserResponse getUserById(Long userId);

    boolean updateUser(Long userId, UserRequest userRequest);

    boolean deleteUser(Long userId);

    // quản trị
    List<UserResponse> getAllUsers();
}
