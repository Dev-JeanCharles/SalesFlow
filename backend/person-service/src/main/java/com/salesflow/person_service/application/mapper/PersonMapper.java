package com.salesflow.person_service.application.mapper;

import com.salesflow.person_service.application.dto.PersonRequestDto;
import com.salesflow.person_service.application.dto.PersonResponseDto;
import com.salesflow.person_service.domain.models.Person;

import java.time.format.DateTimeFormatter;

public class PersonMapper {
    public static Person toDomain(PersonRequestDto request) {
        return new Person(
                request.name(),
                request.taxIdentifier(),
                request.birthDate()
        );
    }

    public static PersonResponseDto toDto(Person person) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new PersonResponseDto(
                person.getPersonId(),
                person.getName(),
                person.getTaxIdentifier(),
                person.getPersonStatus(),
                person.getCreated().toLocalDate().format(formatter),
                person.getBirthDate()
        );
    }
}
