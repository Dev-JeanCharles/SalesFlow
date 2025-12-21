package com.salesflow.plan_service.application.service;

import com.salesflow.plan_service.application.dto.PlanRequestDto;
import com.salesflow.plan_service.application.dto.PlanResponseDto;
import com.salesflow.plan_service.application.mapper.PlanMapper;
import com.salesflow.plan_service.application.port.in.CreatePlanUseCase;
import com.salesflow.plan_service.domain.model.Plan;
import com.salesflow.plan_service.domain.port.in.PlanRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlanService  implements CreatePlanUseCase {

    private final PlanRepositoryPort planRepositoryPort;

    public PlanService(PlanRepositoryPort planRepositoryPort) {
        this.planRepositoryPort = planRepositoryPort;
    }

    @Override
    public PlanResponseDto createPlan(PlanRequestDto request) {
        var planSaved = planRepositoryPort.save(PlanMapper.toDomain(request));
        return PlanMapper.toDto(planSaved);
    }
}