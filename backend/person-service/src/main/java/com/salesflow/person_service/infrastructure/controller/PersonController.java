package com.salesflow.person_service.infrastructure.controller;

import com.salesflow.person_service.application.dto.PersonRequestDto;
import com.salesflow.person_service.application.dto.PersonResponseDto;
import com.salesflow.person_service.application.porters.in.CreatePersonUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final CreatePersonUseCase createPersonUseCase;

    @PostMapping
    public ResponseEntity<PersonResponseDto> createPerson(@RequestBody PersonRequestDto request) {

        PersonResponseDto saved = createPersonUseCase.createPerson(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
