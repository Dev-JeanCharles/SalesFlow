package com.salesflow.person_service.application.porters.in;

import com.salesflow.person_service.application.dto.PersonResponseDto;

public interface GetPersonByIdUseCase {
    PersonResponseDto getPersonById(String personId);
}
