package com.salesflow.plan_service.application.dto;

import com.salesflow.plan_service.domain.enums.TypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PlanResponseDto(
        String planId,
        String name,
        TypeEnum type,
        BigDecimal monthlyPrice,
        String created, boolean active,
        String description
) {}