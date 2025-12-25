package com.salesflow.plan_service.application.port.in;

import com.salesflow.plan_service.application.dto.PlanRequestDto;
import com.salesflow.plan_service.application.dto.PlanResponseDto;

public interface GetPlanByIdUseCase {
    PlanResponseDto getById(String planId);
}