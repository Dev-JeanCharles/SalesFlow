package com.salesflow.sales_service.application.service;

import com.salesflow.sales_service.application.dto.SaleRequestDto;
import com.salesflow.sales_service.application.exceptions.types.ProposalNotAllowedException;
import com.salesflow.sales_service.application.port.in.CreateSalesUseCase;
import com.salesflow.sales_service.application.port.out.PersonPort;
import com.salesflow.sales_service.application.port.out.PlanPort;
import com.salesflow.sales_service.domain.enums.PersonStatus;
import com.salesflow.sales_service.infrastructure.gateway.dto.PersonDto;
import com.salesflow.sales_service.infrastructure.gateway.dto.PlanDto;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PlanService implements CreateSalesUseCase {

    private final PersonPort personPort;
    private final PlanPort planPort;

    public PlanService(PersonPort personPort, PlanPort planPort) {
        this.personPort = personPort;
        this.planPort = planPort;
    }

    @Override
    public void createPlan(SaleRequestDto request) {

        var person = validatePerson(request.getTaxIdentifier());
        var plan = validatePlan(request.getPlanId());

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

}
