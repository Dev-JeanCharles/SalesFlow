package com.salesflow.plan_service.infrastructure.persistence.adpter;

import com.salesflow.plan_service.domain.model.Plan;
import com.salesflow.plan_service.domain.port.in.PlanRepositoryPort;
import com.salesflow.plan_service.infrastructure.persistence.entity.PlanJpa;
import com.salesflow.plan_service.infrastructure.persistence.repository.PlanJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class PlanRepositoryAdapter implements PlanRepositoryPort {

    private final PlanJpaRepository repository;

    public PlanRepositoryAdapter(PlanJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Plan save(Plan plan) {
        PlanJpa jpa = new PlanJpa(
                plan.getPlanId(),
                plan.getName(),
                plan.getType(),
                plan.getMonthlyPrice(),
                plan.getCreated(),
                plan.isActive(),
                plan.getDescription()
        );
        PlanJpa saved = repository.save(jpa);

        return new Plan(
                saved.getPlanId(),
                saved.getName(),
                saved.getType(),
                saved.getMonthlyPrice(),
                saved.getCreated(),
                saved.isActive(),
                saved.getDescription()
        );
    }
}