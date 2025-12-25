package com.salesflow.plan_service.application.service;

import com.salesflow.plan_service.application.dto.PlanRequestDto;
import com.salesflow.plan_service.application.dto.PlanResponseDto;
import com.salesflow.plan_service.domain.enums.TypeEnum;
import com.salesflow.plan_service.domain.model.Plan;
import com.salesflow.plan_service.domain.port.in.PlanRepositoryPort;
import com.salesflow.plan_service.infrastructure.persistence.generator.PlanIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

    @Mock
    private PlanRepositoryPort planRepositoryPort;

    @Mock
    private PlanIdGenerator planIdGenerator;

    @InjectMocks
    private PlanService planService;

    private PlanRequestDto defaultRequest;

    @BeforeEach
    void setup() {
        defaultRequest = new PlanRequestDto(
                "Plano Controle",
                TypeEnum.CONTROLE,
                BigDecimal.valueOf(99.90),
                true,
                "Plano padrão"
        );
    }

    @Test
    void shouldCreateActivePlanSuccessfully() {
        when(planIdGenerator.nextId()).thenReturn("PL000001");
        when(planRepositoryPort.save(any(Plan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PlanResponseDto response = planService.createPlan(defaultRequest);

        assertThat(response.planId()).isEqualTo("PL000001");
        assertThat(response.active()).isTrue();
    }

    @Test
    void shouldCreateInactivePlan() {
        PlanRequestDto inactivePlan = new PlanRequestDto(
                "Plano Básico",
                TypeEnum.PRE_PAGO,
                BigDecimal.valueOf(29.90),
                false,
                "Plano inativo"
        );

        when(planIdGenerator.nextId()).thenReturn("PL000002");
        when(planRepositoryPort.save(any(Plan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PlanResponseDto response = planService.createPlan(inactivePlan);

        assertThat(response.active()).isFalse();
        assertThat(response.name()).isEqualTo("Plano Básico");
    }

    @Test
    void shouldAllowNullDescription() {
        PlanRequestDto requestWithoutDescription = new PlanRequestDto(
                "Plano Light",
                TypeEnum.POS_PAGO,
                BigDecimal.valueOf(149.90),
                true,
                null
        );

        when(planIdGenerator.nextId()).thenReturn("PL000003");
        when(planRepositoryPort.save(any(Plan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PlanResponseDto response = planService.createPlan(requestWithoutDescription);

        assertThat(response.description()).isNull();
    }

    @Test
    void shouldThrowExceptionWhenIdGenerationFails() {
        when(planIdGenerator.nextId())
                .thenThrow(new RuntimeException("Erro ao gerar ID"));

        assertThatThrownBy(() -> planService.createPlan(defaultRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erro ao gerar ID");

        verify(planRepositoryPort, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRepositoryFails() {
        when(planIdGenerator.nextId()).thenReturn("PL000004");
        when(planRepositoryPort.save(any(Plan.class)))
                .thenThrow(new RuntimeException("Erro ao salvar no banco"));

        assertThatThrownBy(() -> planService.createPlan(defaultRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erro ao salvar no banco");
    }

    @Test
    void shouldSetCreationDateAutomatically() {
        when(planIdGenerator.nextId()).thenReturn("PL000005");

        ArgumentCaptor<Plan> captor = ArgumentCaptor.forClass(Plan.class);

        when(planRepositoryPort.save(any(Plan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        planService.createPlan(defaultRequest);

        verify(planRepositoryPort).save(captor.capture());

        assertThat(captor.getValue().getCreated()).isNotNull();
    }

    @Test
    void shouldGenerateIdBeforeSavingPlan() {
        when(planIdGenerator.nextId()).thenReturn("PL000006");
        when(planRepositoryPort.save(any(Plan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        planService.createPlan(defaultRequest);

        InOrder inOrder = inOrder(planIdGenerator, planRepositoryPort);

        inOrder.verify(planIdGenerator).nextId();
        inOrder.verify(planRepositoryPort).save(any(Plan.class));
    }

    @Test
    void shouldMapAllFieldsCorrectly() {
        when(planIdGenerator.nextId()).thenReturn("PL000007");
        when(planRepositoryPort.save(any(Plan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PlanResponseDto response = planService.createPlan(defaultRequest);

        assertThat(response.planId()).isEqualTo("PL000007");
        assertThat(response.name()).isEqualTo(defaultRequest.name());
        assertThat(response.type()).isEqualTo(defaultRequest.type());
        assertThat(response.monthlyPrice()).isEqualByComparingTo(defaultRequest.monthlyPrice());
        assertThat(response.active()).isEqualTo(defaultRequest.active());
        assertThat(response.description()).isEqualTo(defaultRequest.description());
    }
}