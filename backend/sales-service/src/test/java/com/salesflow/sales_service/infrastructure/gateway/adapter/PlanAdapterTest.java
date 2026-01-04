package com.salesflow.sales_service.infrastructure.gateway.adapter;

import com.salesflow.sales_service.infrastructure.gateway.PlanGateway;
import com.salesflow.sales_service.infrastructure.gateway.config.SessionCookieGenerator;
import com.salesflow.sales_service.infrastructure.gateway.dto.PlanDto;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanAdapterTest {

    @Mock
    private PlanGateway planGateway;

    @Mock
    private SessionCookieGenerator cookieGenerator;

    @InjectMocks
    private PlanAdapter adapter;

    // =========================
    // SUCCESS
    // =========================
    @Test
    void shouldReturnPlanWhenFound() {

        PlanDto planDto = new PlanDto(
                "PLAN_001",
                "Plano Gold",
                "SUBSCRIPTION",
                BigDecimal.valueOf(99.90),
                true,
                "Plano premium"
        );

        when(cookieGenerator.generate())
                .thenReturn("JSESSIONID=abc");

        when(planGateway.getPlan(
                eq("PLAN_001"),
                eq("test"),
                eq("JSESSIONID=abc")
        )).thenReturn(planDto);

        Optional<PlanDto> result =
                adapter.getPlanById("PLAN_001");

        assertTrue(result.isPresent());
        assertEquals("PLAN_001", result.get().planId());
        assertTrue(result.get().active());

        verify(cookieGenerator).generate();
        verify(planGateway).getPlan(any(), any(), any());
    }

    // =========================
    // NOT FOUND (404)
    // =========================
    @Test
    void shouldReturnEmptyWhenPlanNotFound() {

        when(cookieGenerator.generate())
                .thenReturn("JSESSIONID=abc");

        FeignException notFound =
                mock(FeignException.NotFound.class);

        when(planGateway.getPlan(
                any(),
                any(),
                any()
        )).thenThrow(notFound);

        Optional<PlanDto> result =
                adapter.getPlanById("PLAN_404");

        assertTrue(result.isEmpty());

        verify(cookieGenerator).generate();
        verify(planGateway).getPlan(any(), any(), any());
    }

    // =========================
    // FEIGN ERROR
    // =========================
    @Test
    void shouldThrowExceptionWhenFeignErrorOccurs() {

        when(cookieGenerator.generate())
                .thenReturn("JSESSIONID=abc");

        FeignException feignException =
                mock(FeignException.class);

        when(feignException.status()).thenReturn(500);

        when(planGateway.getPlan(
                any(),
                any(),
                any()
        )).thenThrow(feignException);

        assertThrows(
                FeignException.class,
                () -> adapter.getPlanById("PLAN_500")
        );

        verify(cookieGenerator).generate();
        verify(planGateway).getPlan(any(), any(), any());
    }
}
