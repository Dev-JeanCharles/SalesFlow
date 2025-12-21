package com.salesflow.person_service.application.porters.out;

import com.salesflow.person_service.domain.models.Person;

public interface PersonRepositoryPort {
    Person save(Person person);
}
