package com.salesflow.plan_service.application.dto;

import com.salesflow.plan_service.domain.enums.TypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PlanRequestDto(
        @NotBlank(message = "O nome do plano é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String name,

        @NotNull(message = "Campo obrigatório")
        TypeEnum type,
        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal monthlyPrice,
        boolean active,
        String description
) {}