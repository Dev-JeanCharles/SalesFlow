package com.salesflow.sales_service.mock;

import com.salesflow.sales_service.domain.model.Sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaleMock {

    public static Sale withFirstPaymentPaid() {
        return Sale.create(
                "SALE001",
                "12345678900",
                "PLAN001",
                "João Silva",
                "Plano Gold",
                "MONTHLY",
                BigDecimal.valueOf(100),
                BigDecimal.ZERO,
                LocalDateTime.now().minusDays(1),
                5,
                BillingHistoryMock.paidFirst()
        );
    }

    public static Sale pendingWithFirstPaymentPending() {
        return Sale.create(
                "SALE002",
                "12345678900",
                "PLAN001",
                "João Silva",
                "Plano Gold",
                "MONTHLY",
                BigDecimal.valueOf(100),
                BigDecimal.ZERO,
                LocalDateTime.now(),
                5,
                BillingHistoryMock.pendingFirst()
        );
    }

    public static Sale pendingWithoutFirstInstallment() {
        return Sale.create(
                "SALE003",
                "12345678900",
                "PLAN001",
                "João Silva",
                "Plano Gold",
                "MONTHLY",
                BigDecimal.valueOf(100),
                BigDecimal.ZERO,
                LocalDateTime.now(),
                5,
                BillingHistoryMock.withoutFirst()
        );
    }
}
