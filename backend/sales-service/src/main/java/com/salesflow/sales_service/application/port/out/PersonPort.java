package com.salesflow.sales_service.application.port.out;

import com.salesflow.sales_service.infrastructure.gateway.dto.PersonDto;

import java.util.Optional;

public interface PersonPort {
    Optional<PersonDto> getPersonById(String personId);
}
