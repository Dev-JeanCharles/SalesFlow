package com.salesflow.sales_service.mock;

import com.salesflow.sales_service.infrastructure.gateway.dto.PlanDto;

import java.math.BigDecimal;

public class PlanDtoMock {

    public static PlanDto active() {
        return new PlanDto(
                "PLAN001",
                "Plano Gold",
                "PREMIUM",
                new BigDecimal("100.00"),
                true,
                "melhor plano da regiao"
        );
    }

    public static PlanDto inactive() {
        return new PlanDto(
                "PLAN001",
                "Plano Gold",
                "PREMIUM",
                new BigDecimal("100.00"),
                false,
                "melhor plano da regiao"
        );
    }
}
