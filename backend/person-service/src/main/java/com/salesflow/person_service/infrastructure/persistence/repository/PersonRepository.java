package com.salesflow.person_service.infrastructure.persistence.repository;

import com.salesflow.person_service.infrastructure.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
}
