package com.salesflow.plan_service.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesflow.plan_service.application.dto.PlanRequestDto;
import com.salesflow.plan_service.application.dto.PlanResponseDto;
import com.salesflow.plan_service.application.port.in.CreatePlanUseCase;
import com.salesflow.plan_service.domain.enums.TypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanController.class)
@AutoConfigureMockMvc(addFilters = false)
class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreatePlanUseCase createPlanUseCase;

    @Test
    void shouldCreatePlanSuccessfully() throws Exception {
        PlanRequestDto request = new PlanRequestDto(
                "Plano Controle",
                TypeEnum.CONTROLE,
                BigDecimal.valueOf(99.90),
                true,
                "Plano de telefonia controle"
        );

        PlanResponseDto response = new PlanResponseDto(
                "PLN123",
                "Plano Controle",
                TypeEnum.CONTROLE,
                BigDecimal.valueOf(99.90),
                "2025-12-24T20:00:00",
                true,
                "Plano de telefonia controle"
        );

        when(createPlanUseCase.createPlan(any(PlanRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.plan_id").value("PLN123"))
                .andExpect(jsonPath("$.name").value("Plano Controle"))
                .andExpect(jsonPath("$.type").value("CONTROLE"))
                .andExpect(jsonPath("$.monthly_price").value(99.9))
                .andExpect(jsonPath("$.active").value(true));


        verify(createPlanUseCase, times(1))
                .createPlan(any(PlanRequestDto.class));
    }

    @Test
    void shouldReturn400WhenEnumIsInvalid() throws Exception {
        String invalidPayload = """
                {
                  "name": "Plano Controle",
                  "type": "CONTROL",
                  "monthlyPrice": 99.90,
                  "active": true,
                  "description": "Plano inválido"
                }
                """;

        mockMvc.perform(post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_PLAN_TYPE"))
                .andExpect(jsonPath("$.message")
                        .value("Tipo de plano inválido: CONTROL"));

        verifyNoInteractions(createPlanUseCase);
    }

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {
        String payload = """
                {
                  "name": "",
                  "type": "CONTROLE",
                  "monthlyPrice": 99.90,
                  "active": true,
                  "description": "Plano inválido"
                }
                """;

        mockMvc.perform(post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));

        verifyNoInteractions(createPlanUseCase);
    }
}
