package com.salesflow.sales_service.infrastructure.gateway.dto;

import com.salesflow.sales_service.domain.enums.PersonStatus;

public record PersonDto(
        String personId,
        String name,
        String taxIdentifier,
        String status,
        String birthDate
) {
    public PersonStatus getStatusEnum() {
        return PersonStatus.from(this.status);
    }
}