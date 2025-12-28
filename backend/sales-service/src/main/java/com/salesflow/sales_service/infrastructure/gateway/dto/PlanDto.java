package com.salesflow.sales_service.infrastructure.gateway.dto;

import java.math.BigDecimal;

public record PlanDto(
        String planId,
        String name,
        String type,
        BigDecimal monthlyPrice,
        boolean active,
        String description
) {}