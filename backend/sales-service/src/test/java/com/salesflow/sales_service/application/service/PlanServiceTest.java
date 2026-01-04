package com.salesflow.sales_service.application.service;

import com.salesflow.sales_service.application.exceptions.types.ProposalNotAllowedException;
import com.salesflow.sales_service.application.port.out.PersonPort;
import com.salesflow.sales_service.application.port.out.PlanPort;
import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import com.salesflow.sales_service.mock.PersonDtoMock;
import com.salesflow.sales_service.mock.PlanDtoMock;
import com.salesflow.sales_service.mock.SaleRequestMock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

    private PlanService service;

    @Mock
    private PersonPort personPort;

    @Mock
    private PlanPort planPort;

    @Mock
    private SaleRepositoryPort repositoryPort;

    @Mock
    private MeterRegistry meterRegistry;

    @Mock
    private Counter counter;



    @BeforeEach
    void setup() {

        // ⚠️ Stub ANTES de criar o service
        when(meterRegistry.counter("sales.created.total"))
                .thenReturn(counter);

        service = new PlanService(
                personPort,
                planPort,
                repositoryPort,
                meterRegistry
        );
    }

    @Test
    void shouldCreateSaleSuccessfully() {

        when(personPort.getPersonById(any()))
                .thenReturn(Optional.of(PersonDtoMock.active()));

        when(planPort.getPlanById(any()))
                .thenReturn(Optional.of(PlanDtoMock.active()));

        when(repositoryPort.existsActiveSaleByTaxIdentifier(any()))
                .thenReturn(false);

        service.createSale(SaleRequestMock.validService());

        verify(repositoryPort).save(any(Sale.class));
        verify(counter).increment();
    }

    @Test
    void shouldThrowExceptionWhenPersonNotFound() {

        when(personPort.getPersonById(any()))
                .thenReturn(Optional.empty());

        assertThrows(
                ProposalNotAllowedException.class,
                () -> service.createSale(SaleRequestMock.validService())
        );

        verify(repositoryPort, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenPersonIsInactive() {

        when(personPort.getPersonById(any()))
                .thenReturn(Optional.of(PersonDtoMock.inactive()));

        assertThrows(
                ProposalNotAllowedException.class,
                () -> service.createSale(SaleRequestMock.validService())
        );

        verify(repositoryPort, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenPlanNotFound() {

        when(personPort.getPersonById(any()))
                .thenReturn(Optional.of(PersonDtoMock.active()));

        when(planPort.getPlanById(any()))
                .thenReturn(Optional.empty());

        assertThrows(
                ProposalNotAllowedException.class,
                () -> service.createSale(SaleRequestMock.validService())
        );
    }

    @Test
    void shouldThrowExceptionWhenPlanIsInactive() {

        when(personPort.getPersonById(any()))
                .thenReturn(Optional.of(PersonDtoMock.active()));

        when(planPort.getPlanById(any()))
                .thenReturn(Optional.of(PlanDtoMock.inactive()));

        assertThrows(
                ProposalNotAllowedException.class,
                () -> service.createSale(SaleRequestMock.validService())
        );
    }


    @Test
    void shouldThrowExceptionWhenActiveSaleAlreadyExists() {

        when(personPort.getPersonById(any()))
                .thenReturn(Optional.of(PersonDtoMock.active()));

        when(planPort.getPlanById(any()))
                .thenReturn(Optional.of(PlanDtoMock.active()));

        when(repositoryPort.existsActiveSaleByTaxIdentifier(any()))
                .thenReturn(true);

        assertThrows(
                ProposalNotAllowedException.class,
                () -> service.createSale(SaleRequestMock.validService())
        );

        verify(repositoryPort, never()).save(any());
    }

}
