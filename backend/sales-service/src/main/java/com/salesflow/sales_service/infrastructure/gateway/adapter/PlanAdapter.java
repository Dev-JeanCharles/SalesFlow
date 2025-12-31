package com.salesflow.sales_service.infrastructure.gateway.adapter;

import com.salesflow.sales_service.application.port.out.PlanPort;
import com.salesflow.sales_service.infrastructure.gateway.PlanGateway;
import com.salesflow.sales_service.infrastructure.gateway.config.SessionCookieGenerator;
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
    private final SessionCookieGenerator cookieGenerator;

    public PlanAdapter(PlanGateway planGateway, SessionCookieGenerator cookieGenerator) {
        this.planGateway = planGateway;
        this.cookieGenerator = cookieGenerator;
    }

    @Override
    public Optional<PlanDto> getPlanById(String planId) {

        String sessionCookie = cookieGenerator.generate();

        log.info(
                "[GATEWAY][PLAN] Fetching plan | planId={}",
                planId
        );

        try {
            PlanDto plan = planGateway.getPlan(
                    planId,
                    "test",
                    sessionCookie
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
