package com.salesflow.sales_service.application.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BillingScheduleServiceTest {

    @Test
    void shouldGenerate12BillingInstallments() {

        var result = BillingScheduleService.generate(
                BigDecimal.valueOf(100),
                LocalDateTime.of(2025, 1, 10, 10, 0),
                5,
                "CREDIT_CARD"
        );

        assertEquals(12, result.size());
        assertEquals(1, result.get(0).getInstallmentNumber());
        assertEquals("CREDIT_CARD", result.get(0).getPaymentMethod());
    }
}
