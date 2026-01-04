package com.salesflow.sales_service.application.scheduler;


import com.salesflow.sales_service.application.port.out.SqsProducerPort;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import com.salesflow.sales_service.mock.SaleMock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PendingSalesSchedulerTest {

    @Mock
    private SaleRepositoryPort saleRepository;

    @Mock
    private SqsProducerPort sqsProducer;

    private MeterRegistry meterRegistry;
    private PendingSalesScheduler scheduler;

    @BeforeEach
    void setup() {
        meterRegistry = new SimpleMeterRegistry();

        scheduler = new PendingSalesScheduler(
                saleRepository,
                sqsProducer,
                meterRegistry
        );
    }

    @Test
    void shouldDoNothingWhenThereAreNoPendingSales() {
        when(saleRepository.findPendingSales())
                .thenReturn(List.of());

        scheduler.processPendingSales();

        verifyNoInteractions(sqsProducer);

        Counter counter = meterRegistry
                .find("sales_contracts_activated_total")
                .counter();

        // métrica não deve existir ou não incrementar
        if (counter != null) {
            assertEquals(0.0, counter.count());
        }
    }

    @Test
    void shouldSendMessageAndIncrementCounterWhenFirstInstallmentIsPaid() {
        when(saleRepository.findPendingSales())
                .thenReturn(List.of(SaleMock.withFirstPaymentPaid()));

        scheduler.processPendingSales();

        verify(sqsProducer)
                .sendSaleActivatedMessage("SALE001");


        Counter counter = meterRegistry
                .find("sales_contracts_activated_total")
                .counter();

        assertNotNull(counter);
        assertEquals(1.0, counter.count());
    }

    @Test
    void shouldNotSendMessageWhenFirstInstallmentIsNotPaid() {
        when(saleRepository.findPendingSales())
                .thenReturn(List.of(SaleMock.pendingWithFirstPaymentPending()));

        scheduler.processPendingSales();

        verifyNoInteractions(sqsProducer);

        Counter counter = meterRegistry
                .find("sales_contracts_activated_total")
                .counter();

        if (counter != null) {
            assertEquals(0.0, counter.count());
        }
    }
}
