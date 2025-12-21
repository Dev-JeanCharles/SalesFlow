package com.salesflow.plan_service.application.dto;

import com.salesflow.plan_service.domain.enums.TypeEnum;

import java.math.BigDecimal;

public record PlanRequestDto(
        String planId,
        String name,
        TypeEnum type,
        BigDecimal monthlyPrice,
        boolean active,
        String description
) {}