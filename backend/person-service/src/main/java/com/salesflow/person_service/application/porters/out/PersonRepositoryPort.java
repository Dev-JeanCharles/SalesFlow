package com.salesflow.person_service.application.porters.out;

import com.salesflow.person_service.application.dto.PersonResponseDto;
import com.salesflow.person_service.domain.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryPort {
    Person save(Person person);
    Optional<Person> findById(String personId);
    List<PersonResponseDto> findAll();
    boolean existsByTaxIdentifierOrNameAndBirthDate(String taxIdentifier, String name, java.time.LocalDate birthDate);
}
