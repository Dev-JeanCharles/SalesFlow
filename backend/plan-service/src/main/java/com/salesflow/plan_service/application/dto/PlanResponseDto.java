package com.salesflow.plan_service.application.dto;

import com.salesflow.plan_service.domain.enums.TypeEnum;

import java.math.BigDecimal;

public record PlanResponseDto(
        String planId,
        String name,
        TypeEnum type,
        BigDecimal monthlyPrice,
        java.time.LocalDateTime created, boolean active,
        String description
) {}