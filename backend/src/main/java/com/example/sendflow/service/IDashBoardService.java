package com.example.sendflow.service;

import com.example.sendflow.dto.response.DashboardAdminResponse;
import com.example.sendflow.dto.response.DashboardUserResponse;
import com.example.sendflow.dto.response.RevenueResponse;

import java.util.List;

public interface IDashBoardService {
    // user
    DashboardUserResponse getAllDashBoardUser(Long userId);

    // admin
    DashboardAdminResponse getAllDashBoardAdmin();

    // revenue
    List<RevenueResponse> getAllRevenue(int year, int month);
}
