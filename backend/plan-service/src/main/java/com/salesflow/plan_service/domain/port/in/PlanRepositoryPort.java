package com.salesflow.plan_service.domain.port.in;

import com.salesflow.plan_service.domain.model.Plan;

import java.util.Optional;

public interface PlanRepositoryPort {
    Plan save(Plan plan);

    Optional<Plan> findById(String planId);
}