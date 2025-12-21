package com.salesflow.person_service.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesflow.person_service.domain.enums.PersonStatusEnum;

import java.time.LocalDate;

public record PersonResponseDto(
        String personId,
        String name,
        String taxIdentifier,
        PersonStatusEnum status,
        String created,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate birthDate

){}
