package com.salesflow.person_service.infrastructure.persistence.adapter;

import com.salesflow.person_service.application.porters.out.PersonRepositoryPort;
import com.salesflow.person_service.domain.models.Person;
import com.salesflow.person_service.infrastructure.persistence.entity.PersonEntity;
import com.salesflow.person_service.infrastructure.persistence.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
