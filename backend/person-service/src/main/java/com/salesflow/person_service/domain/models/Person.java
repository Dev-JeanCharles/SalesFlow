package com.salesflow.person_service.domain.models;

import com.salesflow.person_service.domain.enums.PersonStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Person {
    private String personId;
    private String name;
    private String taxIdentifier;
    private PersonStatusEnum personStatus;
    private OffsetDateTime created;
    private LocalDate birthDate;

    public Person(String name, String taxIdentifier, LocalDate birthDate) {
        this.name = name;
        this.taxIdentifier = taxIdentifier;
        this.birthDate = birthDate;
        this.personStatus = PersonStatusEnum.ACTIVE;
        this.created = OffsetDateTime.now();
    }
}
