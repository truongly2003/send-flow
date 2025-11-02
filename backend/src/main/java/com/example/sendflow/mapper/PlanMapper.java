package com.example.sendflow.mapper;

import com.example.sendflow.dto.request.PlanRequest;
import com.example.sendflow.dto.response.PlanResponse;
import com.example.sendflow.entity.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PlanMapper {
    Plan toPlan(PlanRequest planRequest);
    PlanResponse toPlanResponse(Plan plan);

    void updatePlanFromRequest(PlanRequest request, @MappingTarget Plan existingPlan);
}
