package com.example.sendflow.service;

import com.example.sendflow.dto.response.DashboardAdminResponse;
import com.example.sendflow.dto.response.DashboardUserResponse;

public interface IDashBoardService {
    // user
    DashboardUserResponse getAllDashBoardUser(Long userId);

    // admin
    DashboardAdminResponse getAllDashBoardAdmin();
}
