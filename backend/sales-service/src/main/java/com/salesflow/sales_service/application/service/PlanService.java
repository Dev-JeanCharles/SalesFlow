package com.salesflow.sales_service.application.service;

import com.salesflow.sales_service.application.dto.SaleRequestDto;
import com.salesflow.sales_service.application.exceptions.types.ProposalNotAllowedException;
import com.salesflow.sales_service.application.port.in.CreateSalesUseCase;
import com.salesflow.sales_service.application.port.out.PersonPort;
import com.salesflow.sales_service.application.port.out.PlanPort;
import com.salesflow.sales_service.domain.enums.PersonStatus;
import com.salesflow.sales_service.domain.model.BillingHistory;
import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import com.salesflow.sales_service.infrastructure.gateway.dto.PersonDto;
import com.salesflow.sales_service.infrastructure.gateway.dto.PlanDto;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PlanService implements CreateSalesUseCase {

    private final Counter salesCreatedCounter;
    private static final Logger log = LoggerFactory.getLogger(PlanService.class);

    private final PersonPort personPort;
    private final PlanPort planPort;
    private final SaleRepositoryPort repositoryPort;


    public PlanService(
            PersonPort personPort,
            PlanPort planPort,
            SaleRepositoryPort repositoryPort,
            MeterRegistry meterRegistry
    ) {
        this.personPort = personPort;
        this.planPort = planPort;
        this.repositoryPort = repositoryPort;
        this.salesCreatedCounter = meterRegistry.counter("sales.created.total");
    }

    @Override
    public void createSale(SaleRequestDto request) {

        log.info(
                "[SERVICE][SALE] Starting sale creation | taxIdentifier={} | planId={}",
                request.getTaxIdentifier(),
                request.getPlanId()
        );

        var person = validatePerson(request.getTaxIdentifier());
        var plan = validatePlan(request.getPlanId());
        validateNoActiveSale(person.taxIdentifier());

        LocalDateTime startDate = LocalDateTime.now();

        List<BillingHistory> billingHistory =
                BillingScheduleService.generate(
                        plan.monthlyPrice(),
                        startDate,
                        request.getBillingDay(),
                        request.getBilling().getPaymentMethod()
                );

        Sale sale = Sale.create(
                UUID.randomUUID().toString().substring(0, 8),
                person.taxIdentifier(),
                plan.planId(),
                person.name(),
                plan.name(),
                plan.type(),
                plan.monthlyPrice(),
                request.getDiscount(),
                LocalDateTime.now(),
                request.getBillingDay(),
                billingHistory
        );

        repositoryPort.save(sale);

        salesCreatedCounter.increment();

        log.info(
                "[SERVICE][SALE] Sale created successfully | saleId={} | taxIdentifier={}",
                sale.getSaleId(),
                sale.getTaxIdentifier()
        );
    }

    private PersonDto validatePerson(String taxIdentifier) {

        log.debug(
                "[SERVICE][SALE] Validating person | taxIdentifier={}",
                taxIdentifier
        );

        PersonDto person = personPort.getPersonById(taxIdentifier)
                .orElseThrow(() -> {
                    log.warn(
                            "[SERVICE][SALE] Person not found | taxIdentifier={}",
                            taxIdentifier
                    );
                    return new ProposalNotAllowedException(
                            "Não é possível oferecer proposta: cliente não existe"
                    );
                });

        if (!Objects.equals(person.status(), PersonStatus.ACTIVE.name())) {
            log.warn(
                    "[SERVICE][SALE] Person inactive | taxIdentifier={} | status={}",
                    taxIdentifier,
                    person.status()
            );
            throw new ProposalNotAllowedException(
                    "Não é possível oferecer proposta: cliente com status " + person.status()
            );
        }

        return person;
    }

    private PlanDto validatePlan(String planId) {

        log.debug(
                "[SERVICE][SALE] Validating plan | planId={}",
                planId
        );

        PlanDto plan = planPort.getPlanById(planId)
                .orElseThrow(() -> {
                    log.warn(
                            "[SERVICE][SALE] Plan not found | planId={}",
                            planId
                    );
                    return new ProposalNotAllowedException(
                            "Não é possível oferecer proposta: plano não existe"
                    );
                });

        if (!plan.active()) {
            log.warn(
                    "[SERVICE][SALE] Plan inactive | planId={}",
                    planId
            );
            throw new ProposalNotAllowedException(
                    "Não é possível oferecer proposta: plano com status inativo"
            );
        }

        return plan;
    }

    private void validateNoActiveSale(String taxIdentifier) {

        log.debug(
                "[SERVICE][SALE] Checking active sale | taxIdentifier={}",
                taxIdentifier
        );

        if (repositoryPort.existsActiveSaleByTaxIdentifier(taxIdentifier)) {
            log.warn(
                    "[SERVICE][SALE] Active sale already exists | taxIdentifier={}",
                    taxIdentifier
            );
            throw new ProposalNotAllowedException(
                    "Não é possível oferecer proposta: cliente já possui um plano ativo"
            );
        }
    }
}
