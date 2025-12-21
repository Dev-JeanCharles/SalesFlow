package com.salesflow.plan_service.application.mapper;

import com.salesflow.plan_service.application.dto.PlanRequestDto;
import com.salesflow.plan_service.application.dto.PlanResponseDto;
import com.salesflow.plan_service.domain.model.Plan;

import java.time.LocalDateTime;

public class PlanMapper {

    public static Plan toDomain(PlanRequestDto request) {
        return new Plan(
                request.name(),
                request.type(),
                request.monthlyPrice(),
                LocalDateTime.now(),
                request.active(),
                request.description()
        );
    }

    public static PlanResponseDto toDto(Plan plan) {
        return new PlanResponseDto(
                plan.getPlanId(),
                plan.getName(),
                plan.getType(),
                plan.getMonthlyPrice(),
                plan.getCreated(),
                plan.isActive(),
                plan.getDescription()
        );
    }
}