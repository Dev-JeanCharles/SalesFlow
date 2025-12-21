package com.salesflow.person_service.application.porters.in;

import com.salesflow.person_service.application.dto.PersonResponseDto;

import java.util.List;

public interface GetAllPersonsUseCase {
    List<PersonResponseDto> getAllPersons();
}
