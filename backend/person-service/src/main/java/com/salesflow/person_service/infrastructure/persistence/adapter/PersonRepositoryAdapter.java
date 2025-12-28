package com.salesflow.person_service.infrastructure.persistence.adapter;

import com.salesflow.person_service.application.dto.PersonResponseDto;
import com.salesflow.person_service.application.porters.out.PersonRepositoryPort;
import com.salesflow.person_service.domain.models.Person;
import com.salesflow.person_service.infrastructure.persistence.entity.PersonEntity;
import com.salesflow.person_service.infrastructure.persistence.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonRepositoryAdapter implements PersonRepositoryPort {

    private final PersonRepository repository;

    @Override
    public Person save(Person person) {
        PersonEntity entity = new PersonEntity(
                person.getPersonId(),
                person.getName(),
                person.getTaxIdentifier(),
                person.getPersonStatus(),
                person.getCreated(),
                person.getBirthDate()
        );
        PersonEntity saved = repository.save(entity);

        return new Person(
                saved.getPersonId(),
                saved.getName(),
                saved.getTaxIdentifier(),
                saved.getPersonStatus(),
                saved.getCreated(),
                saved.getBirthDate()
        );
    }

    @Override
    public Optional<Person> findById(String personId) {
        return repository.findById(personId)
                .map(saved -> new Person(
                        saved.getPersonId(),
                        saved.getName(),
                        saved.getTaxIdentifier(),
                        saved.getPersonStatus(),
                        saved.getCreated(),
                        saved.getBirthDate()
                ));
    }

    @Override
    public List<PersonResponseDto> findAll() {
        return repository.findAll().stream()
                .map(entity -> new PersonResponseDto(
                        entity.getPersonId(),
                        entity.getName(),
                        entity.getTaxIdentifier(),
                        entity.getPersonStatus(),
                        entity.getCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        entity.getBirthDate()
                ))
                .toList();
    }


    @Override
    public boolean existsByTaxIdentifierOrNameAndBirthDate(String taxIdentifier, String name, LocalDate birthDate) {
        return repository.existsByTaxIdentifierOrNameAndBirthDate(taxIdentifier, name, birthDate);
    }

    @Override
    public Optional<Person> findByTaxIdentifier(String taxIdentifier) {
        return repository.findByTaxIdentifier(taxIdentifier)
                .map(saved -> new Person(
                        saved.getPersonId(),
                        saved.getName(),
                        saved.getTaxIdentifier(),
                        saved.getPersonStatus(),
                        saved.getCreated(),
                        saved.getBirthDate()
                ));
    }
}
