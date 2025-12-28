package com.salesflow.person_service.application.services;

import com.salesflow.person_service.application.dto.PersonRequestDto;
import com.salesflow.person_service.application.dto.PersonResponseDto;
import com.salesflow.person_service.application.exceptions.types.EntityAlreadyExistsException;
import com.salesflow.person_service.application.exceptions.types.NotFoundException;
import com.salesflow.person_service.application.mapper.PersonMapper;
import com.salesflow.person_service.application.porters.in.CreatePersonUseCase;
import com.salesflow.person_service.application.porters.in.GetAllPersonsUseCase;
import com.salesflow.person_service.application.porters.in.GetPersonByIdUseCase;
import com.salesflow.person_service.application.porters.in.GetPersonByTaxIdentifierUseCase;
import com.salesflow.person_service.application.porters.out.PersonRepositoryPort;
import com.salesflow.person_service.domain.models.Person;
import com.salesflow.person_service.infrastructure.persistence.generator.PersonIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService implements CreatePersonUseCase, GetPersonByIdUseCase, GetAllPersonsUseCase, GetPersonByTaxIdentifierUseCase {

    private final PersonIdGenerator personIdGenerator;
    private final PersonRepositoryPort personRepositoryPort;

    @Override
    public PersonResponseDto createPerson(PersonRequestDto request) {

        boolean exists = personRepositoryPort.existsByTaxIdentifierOrNameAndBirthDate(
                request.taxIdentifier(),
                request.name(),
                request.birthDate()
        );

        if (exists) {
            throw new EntityAlreadyExistsException("Pessoa com os mesmos dados já existe");
        }

        Person person = PersonMapper.toDomain(request);

        person.setPersonId(personIdGenerator.nextId());

        Person saved = personRepositoryPort.save(person);

        return PersonMapper.toDto(saved);
    }

    @Override
    public PersonResponseDto getPersonById(String personId) {
        Person person = personRepositoryPort.findById(personId)
                .orElseThrow(()-> new NotFoundException("Pessoa não encontrada com ID: " + personId));
        return PersonMapper.toDto(person);
    }

    @Override
    public List<PersonResponseDto> getAllPersons() {
        return personRepositoryPort.findAll();
    }

    @Override
    public PersonResponseDto getPerson(String taxIdentifier) {
        Person person = personRepositoryPort.findByTaxIdentifier(taxIdentifier)
                .orElseThrow(()-> new NotFoundException("Pessoa não encontrada com ID: " + taxIdentifier));
        return PersonMapper.toDto(person);
    }
}
