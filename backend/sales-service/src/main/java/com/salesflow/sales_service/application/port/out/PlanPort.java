package com.salesflow.sales_service.application.port.out;

import com.salesflow.sales_service.infrastructure.gateway.dto.PlanDto;

import java.util.Optional;

public interface PlanPort {
    Optional<PlanDto> getPlanById(String planId);
}