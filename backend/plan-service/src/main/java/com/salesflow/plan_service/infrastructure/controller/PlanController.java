package com.salesflow.plan_service.infrastructure.controller;

import com.salesflow.plan_service.application.dto.PlanRequestDto;
import com.salesflow.plan_service.application.dto.PlanResponseDto;
import com.salesflow.plan_service.application.mapper.PlanMapper;
import com.salesflow.plan_service.application.port.in.CreatePlanUseCase;
import com.salesflow.plan_service.application.port.in.GetPlanByIdUseCase;
import com.salesflow.plan_service.application.service.PlanService;
import com.salesflow.plan_service.domain.model.Plan;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
public class PlanController {

    private final CreatePlanUseCase createPlanUseCase;
    private final GetPlanByIdUseCase getPlanByIdUseCase;

    public PlanController(
            CreatePlanUseCase createPlanUseCase,
            GetPlanByIdUseCase getPlanByIdUseCase
    ) {
        this.createPlanUseCase = createPlanUseCase;
        this.getPlanByIdUseCase = getPlanByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<PlanResponseDto> createPlan(@RequestBody @Valid PlanRequestDto request) {

        var saved = createPlanUseCase.createPlan(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<PlanResponseDto> getByPlanId(
            @RequestParam("plan_id") String planId) {

        var plan = getPlanByIdUseCase.getById(planId);
        return ResponseEntity.ok(plan);
    }

}