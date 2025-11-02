package com.example.sendflow.service;

import com.example.sendflow.dto.request.PlanRequest;
import com.example.sendflow.dto.response.PlanResponse;

import java.util.List;

public interface IPlanService {
    List<PlanResponse> getAllPlan();
    PlanResponse createPlan(PlanRequest planRequest);

    PlanResponse updatePlan(Long id, PlanRequest planRequest);

    void deletePlan(Long id);

    PlanResponse getPlanById(Long id);
}
