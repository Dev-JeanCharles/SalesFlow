package com.salesflow.person_service.infrastructure.persistence.repository;

import com.salesflow.person_service.infrastructure.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PersonRepository extends JpaRepository<PersonEntity, String> {

    boolean existsByTaxIdentifierOrNameAndBirthDate(String taxIdentifier, String name, LocalDate birthDate);

}
