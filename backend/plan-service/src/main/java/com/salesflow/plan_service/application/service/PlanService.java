package com.salesflow.plan_service.application.service;

import com.salesflow.plan_service.application.dto.PlanRequestDto;
import com.salesflow.plan_service.application.dto.PlanResponseDto;
import com.salesflow.plan_service.application.mapper.PlanMapper;
import com.salesflow.plan_service.application.port.in.CreatePlanUseCase;
import com.salesflow.plan_service.domain.model.Plan;
import com.salesflow.plan_service.domain.port.in.PlanRepositoryPort;
import com.salesflow.plan_service.infrastructure.persistence.generator.PlanIdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlanService implements CreatePlanUseCase {

    private final PlanRepositoryPort planRepositoryPort;
    private final PlanIdGenerator planIdGenerator;

    public PlanService(PlanRepositoryPort planRepositoryPort,
                       PlanIdGenerator planIdGenerator
    ) {
        this.planRepositoryPort = planRepositoryPort;
        this.planIdGenerator = planIdGenerator;
    }

    @Override
    public PlanResponseDto createPlan(PlanRequestDto request) {

        Plan plan = PlanMapper.toDomain(request);

        plan.setPlanId(planIdGenerator.nextId());

        Plan saved = planRepositoryPort.save(plan);

        return PlanMapper.toDto(saved);
    }
}