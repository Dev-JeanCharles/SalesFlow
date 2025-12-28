package com.salesflow.sales_service.infrastructure.gateway.dto;

public record PlanDto(
        String planId,
        String name,
        String type,
        double monthlyPrice,
        boolean active,
        String description
) {}