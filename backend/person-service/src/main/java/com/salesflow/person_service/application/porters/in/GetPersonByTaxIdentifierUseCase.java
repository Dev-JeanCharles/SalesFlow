package com.salesflow.person_service.application.porters.in;

import com.salesflow.person_service.application.dto.PersonResponseDto;

public interface GetPersonByTaxIdentifierUseCase {
    PersonResponseDto getPerson(String taxIdentifier);
}
