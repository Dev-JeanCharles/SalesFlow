package com.salesflow.person_service.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @PostMapping
    public ResponseEntity<PersonResponseDTO> createPerson(@RequestBody PersonRequestDTO request) {

        PersonResponseDTO saved = createPersonUseCase.createPerson(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
