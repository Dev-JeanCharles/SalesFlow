package com.salesflow.sales_service.application.service;

import com.salesflow.sales_service.application.dto.SaleRequestDto;
import com.salesflow.sales_service.application.exceptions.types.ProposalNotAllowedException;
import com.salesflow.sales_service.application.port.in.CreateSalesUseCase;
import com.salesflow.sales_service.application.port.out.PersonPort;
import com.salesflow.sales_service.application.port.out.PlanPort;
import com.salesflow.sales_service.domain.enums.PersonStatus;
import com.salesflow.sales_service.domain.enums.StatusPaymentEnum;
import com.salesflow.sales_service.domain.model.BillingHistory;
import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import com.salesflow.sales_service.infrastructure.gateway.dto.PersonDto;
import com.salesflow.sales_service.infrastructure.gateway.dto.PlanDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PlanService implements CreateSalesUseCase {

    private final PersonPort personPort;
    private final PlanPort planPort;

    private final SaleRepositoryPort repositoryPort;

    public PlanService(
            PersonPort personPort,
            PlanPort planPort,
            SaleRepositoryPort repositoryPort
    ) {
        this.personPort = personPort;
        this.planPort = planPort;
        this.repositoryPort = repositoryPort;
    }

    @Override
    public void createSale(SaleRequestDto request) {

        var person = validatePerson(request.getTaxIdentifier());
        var plan = validatePlan(request.getPlanId());
        validateNoActiveSale(person.taxIdentifier());

        List<BillingHistory> billingHistory = List.of(
                new BillingHistory(
                        UUID.randomUUID().toString(),
                        plan.monthlyPrice(),
                        StatusPaymentEnum.PENDING,
                        request.getBilling().getNextBillingDate(),
                        request.getBilling().getPaymentMethod()
                )
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



        System.out.println("Person: " + person);
        System.out.println("Plan: " + plan.name());
    }

    private PersonDto validatePerson(String taxIdentifier) {

        PersonDto person = personPort.getPersonById(taxIdentifier)
                .orElseThrow(() ->
                        new ProposalNotAllowedException(
                                "Não é possível oferecer proposta: cliente não existe"
                        )
                );


        if (!Objects.equals(person.status(), PersonStatus.ACTIVE.name())) {
            throw new ProposalNotAllowedException(
                    "Não é possível oferecer proposta: cliente com status " + person.status()
            );
        }

        return person;
    }

    private PlanDto validatePlan(String planId) {

        PlanDto plan = planPort.getPlanById(planId)
                .orElseThrow(() ->
                        new ProposalNotAllowedException(
                                "Não é possível oferecer proposta: plano não existe"
                        )
                );

        if (!plan.active()) {
            throw new ProposalNotAllowedException(
                    "Não é possível oferecer proposta: plano com status inativo"
            );
        }

        return plan;
    }

    private void validateNoActiveSale(String taxIdentifier) {

        if (repositoryPort.existsActiveSaleByTaxIdentifier(taxIdentifier)) {
            throw new ProposalNotAllowedException(
                    "Não é possível oferecer proposta: cliente já possui um plano ativo"
            );
        }
    }

}
