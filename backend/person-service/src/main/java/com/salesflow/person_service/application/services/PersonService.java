package com.salesflow.person_service.application.services;

import com.salesflow.person_service.application.dto.PersonRequestDto;
import com.salesflow.person_service.application.dto.PersonResponseDto;
import com.salesflow.person_service.application.mapper.PersonMapper;
import com.salesflow.person_service.application.porters.in.CreatePersonUseCase;
import com.salesflow.person_service.application.porters.out.PersonRepositoryPort;
import com.salesflow.person_service.domain.models.Person;
import com.salesflow.person_service.infrastructure.persistence.generator.PersonIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService implements CreatePersonUseCase {

    private final PersonIdGenerator personIdGenerator;
    private final PersonRepositoryPort personRepositoryPort;

    @Override
    public PersonResponseDto createPerson(PersonRequestDto request) {

        Person person = PersonMapper.toDomain(request);

        person.setPersonId(personIdGenerator.nextId());

        Person saved = personRepositoryPort.save(person);

        return PersonMapper.toDto(saved);
    }
}
