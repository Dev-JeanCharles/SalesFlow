package com.salesflow.person_service.application.porters.in;

import com.salesflow.person_service.application.dto.PersonRequestDto;
import com.salesflow.person_service.application.dto.PersonResponseDto;

public interface CreatePersonUseCase {

    PersonResponseDto createPerson(PersonRequestDto request);
}
