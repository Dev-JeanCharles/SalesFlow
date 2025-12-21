package com.salesflow.person_service.infrastructure.persistence.entity;

import com.salesflow.person_service.domain.enums.PersonStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonEntity {
    @Id
    @Column(length = 8)
    private String personId;

    private String name;

    private String taxIdentifier;

    @Enumerated(EnumType.STRING)
    private PersonStatusEnum personStatus;

    private OffsetDateTime  created;

    private LocalDate birthDate;
}
