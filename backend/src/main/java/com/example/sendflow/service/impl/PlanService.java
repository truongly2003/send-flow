package com.example.sendflow.service.impl;

import com.example.sendflow.dto.request.PlanRequest;
import com.example.sendflow.dto.response.PlanResponse;
import com.example.sendflow.entity.Plan;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.mapper.PlanMapper;
import com.example.sendflow.repository.PlanRepository;
import com.example.sendflow.service.IPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService implements IPlanService {
    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    @Override
    public List<PlanResponse> getAllPlan() {
        List<Plan> plans = planRepository.findAll();
        return plans.stream()
                .map(planMapper::toPlanResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PlanResponse createPlan(PlanRequest planRequest) {
        Plan plan = planMapper.toPlan(planRequest);
        Plan savedPlan = planRepository.save(plan);
        return planMapper.toPlanResponse(savedPlan);
    }

    @Override
    public PlanResponse updatePlan(Long id, PlanRequest planRequest) {
        Plan existingPlan = planRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
        planMapper.updatePlanFromRequest(planRequest, existingPlan);
        Plan updatedPlan = planRepository.save(existingPlan);
        return planMapper.toPlanResponse(updatedPlan);
    }

    @Override
    public void deletePlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
        planRepository.delete(plan);
    }

    @Override
    public PlanResponse getPlanById(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
        System.out.println(plan);
        return planMapper.toPlanResponse(plan);
    }
}
