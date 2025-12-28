package com.salesflow.person_service.infrastructure.persistence.repository;

import com.salesflow.person_service.domain.models.Person;
import com.salesflow.person_service.infrastructure.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.time.LocalDate;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, String> {

    boolean existsByTaxIdentifierOrNameAndBirthDate(String taxIdentifier, String name, LocalDate birthDate);

    Optional<PersonEntity> findByTaxIdentifier(String taxIdentifier);
}
