package com.salesflow.plan_service.infrastructure.persistence.adpter;

import com.salesflow.plan_service.domain.enums.TypeEnum;
import com.salesflow.plan_service.domain.model.Plan;
import com.salesflow.plan_service.infrastructure.persistence.entity.PlanJpa;
import com.salesflow.plan_service.infrastructure.persistence.repository.PlanJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanRepositoryAdapterTest {

    @Mock
    private PlanJpaRepository repository;

    @InjectMocks
    private PlanRepositoryAdapter adapter;

    private Plan domainPlan;
    private LocalDateTime now;

    @BeforeEach
    void setup() {
        now = LocalDateTime.now();

        domainPlan = new Plan(
                "PL000123",
                "Plano Premium",
                TypeEnum.POS_PAGO,
                BigDecimal.valueOf(199.90),
                now,
                true,
                "Plano completo"
        );
    }

    @Test
    void shouldSavePlanAndReturnDomainObject() {
        // given
        PlanJpa savedJpa = new PlanJpa(
                "PL000123",
                "Plano Premium",
                TypeEnum.POS_PAGO,
                BigDecimal.valueOf(199.90),
                now,
                true,
                "Plano completo"
        );

        when(repository.save(any(PlanJpa.class))).thenReturn(savedJpa);

        // when
        Plan result = adapter.save(domainPlan);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getPlanId()).isEqualTo("PL000123");
        assertThat(result.getName()).isEqualTo("Plano Premium");
        assertThat(result.getType()).isEqualTo(TypeEnum.POS_PAGO);
        assertThat(result.getMonthlyPrice()).isEqualByComparingTo("199.90");
        assertThat(result.getCreated()).isEqualTo(now);
        assertThat(result.isActive()).isTrue();
        assertThat(result.getDescription()).isEqualTo("Plano completo");
    }

    @Test
    void shouldMapDomainToJpaCorrectlyBeforeSaving() {
        ArgumentCaptor<PlanJpa> captor = ArgumentCaptor.forClass(PlanJpa.class);

        when(repository.save(any(PlanJpa.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        adapter.save(domainPlan);

        verify(repository).save(captor.capture());
        PlanJpa jpa = captor.getValue();

        assertThat(jpa.getPlanId()).isEqualTo(domainPlan.getPlanId());
        assertThat(jpa.getName()).isEqualTo(domainPlan.getName());
        assertThat(jpa.getType()).isEqualTo(domainPlan.getType());
        assertThat(jpa.getMonthlyPrice()).isEqualTo(domainPlan.getMonthlyPrice());
        assertThat(jpa.getCreated()).isEqualTo(domainPlan.getCreated());
        assertThat(jpa.isActive()).isEqualTo(domainPlan.isActive());
        assertThat(jpa.getDescription()).isEqualTo(domainPlan.getDescription());
    }

    @Test
    void shouldAllowNullDescription() {
        Plan planWithoutDescription = new Plan(
                "PL000124",
                "Plano Básico",
                TypeEnum.PRE_PAGO,
                BigDecimal.valueOf(49.90),
                now,
                true,
                null
        );

        when(repository.save(any(PlanJpa.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Plan result = adapter.save(planWithoutDescription);

        assertThat(result.getDescription()).isNull();
    }

    @Test
    void shouldPropagateExceptionFromJpaRepository() {
        when(repository.save(any()))
                .thenThrow(new RuntimeException("Erro no banco"));

        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        adapter.save(domainPlan)
                )
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erro no banco");
    }
}
