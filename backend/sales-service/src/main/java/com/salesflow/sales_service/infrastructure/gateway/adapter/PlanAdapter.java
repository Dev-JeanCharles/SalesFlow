package com.salesflow.sales_service.infrastructure.gateway.adapter;

import com.salesflow.sales_service.application.port.out.PlanPort;
import com.salesflow.sales_service.infrastructure.gateway.PlanGateway;
import com.salesflow.sales_service.infrastructure.gateway.dto.PlanDto;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlanAdapter implements PlanPort {

    private final PlanGateway planGateway;

    public PlanAdapter(PlanGateway planGateway) {
        this.planGateway = planGateway;
    }
    @Override
    public Optional<PlanDto> getPlanById(String planId) {
        try {
            return Optional.of(
                    planGateway.getPlan(
                            planId,
                            "test",
                            "JSESSIONID=8D55E404F060E1AC85297A4D1B9817A5"
                    )
            );
        } catch (FeignException.NotFound ex) {
            return Optional.empty();
        }
    }
}