package com.salesflow.plan_service.infrastructure.persistence.repository;

import com.salesflow.plan_service.infrastructure.persistence.entity.PlanJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanJpaRepository extends JpaRepository<PlanJpa, String> {}