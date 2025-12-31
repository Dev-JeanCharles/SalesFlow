package com.salesflow.sales_service.infrastructure.gateway.adapter;

import com.salesflow.sales_service.application.port.out.PlanPort;
import com.salesflow.sales_service.infrastructure.gateway.PlanGateway;
import com.salesflow.sales_service.infrastructure.gateway.dto.PlanDto;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlanAdapter implements PlanPort {

    private static final Logger log = LoggerFactory.getLogger(PlanAdapter.class);

    private final PlanGateway planGateway;

    public PlanAdapter(PlanGateway planGateway) {
        this.planGateway = planGateway;
    }

    @Override
    public Optional<PlanDto> getPlanById(String planId) {

        log.info(
                "[GATEWAY][PLAN] Fetching plan | planId={}",
                planId
        );

        try {
            PlanDto plan = planGateway.getPlan(
                    planId,
                    "test",
                    "JSESSIONID=8D55E404F060E1AC85297A4D1B9817A5"
            );

            log.info(
                    "[GATEWAY][PLAN] Plan found | planId={} | active={}",
                    planId,
                    plan.active()
            );

            return Optional.of(plan);

        } catch (FeignException.NotFound ex) {

            log.warn(
                    "[GATEWAY][PLAN] Plan not found | planId={}",
                    planId
            );
            return Optional.empty();

        } catch (FeignException ex) {

            log.error(
                    "[GATEWAY][PLAN] Error calling Plan service | planId={} | status={} | message={}",
                    planId,
                    ex.status(),
                    ex.getMessage()
            );
            throw ex;
        }
    }
}
