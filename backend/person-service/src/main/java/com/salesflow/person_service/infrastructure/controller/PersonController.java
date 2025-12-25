package com.salesflow.person_service.infrastructure.controller;

import com.salesflow.person_service.application.dto.PersonRequestDto;
import com.salesflow.person_service.application.dto.PersonResponseDto;
import com.salesflow.person_service.application.porters.in.CreatePersonUseCase;
import com.salesflow.person_service.application.porters.in.GetAllPersonsUseCase;
import com.salesflow.person_service.application.porters.in.GetPersonByIdUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final CreatePersonUseCase createPersonUseCase;
    private final GetPersonByIdUseCase getPersonByIdUseCase;
    private final GetAllPersonsUseCase getAllPersonsUseCase;

    @PostMapping
    public ResponseEntity<PersonResponseDto> createPerson(@RequestBody @Valid PersonRequestDto request) {

        PersonResponseDto saved = createPersonUseCase.createPerson(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{all}")
    public ResponseEntity<List<PersonResponseDto>> getAllPersons() {

        List<PersonResponseDto> persons = getAllPersonsUseCase.getAllPersons();

        return ResponseEntity.status(HttpStatus.OK).body(persons);
    }

    @GetMapping
    public ResponseEntity<PersonResponseDto> getPersonById(
            @RequestParam("person_id") String personId) {

        PersonResponseDto person = getPersonByIdUseCase.getPersonById(personId);

        return ResponseEntity.status(HttpStatus.OK).body(person);
    }
}
