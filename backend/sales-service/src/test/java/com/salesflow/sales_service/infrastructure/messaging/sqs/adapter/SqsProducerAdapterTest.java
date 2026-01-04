package com.salesflow.sales_service.infrastructure.messaging.sqs.adapter;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SqsProducerAdapterTest {

    private static final String SALE_ID = "SALE001";
    private static final String QUEUE_NAME = "sale-activation-queue";

    @Mock
    private SqsTemplate sqsTemplate;

    @InjectMocks
    private SqsProducerAdapter producerAdapter;

    @Test
    void shouldSendSaleActivationMessageToQueue() {

        // chama o método que queremos testar
        producerAdapter.sendSaleActivatedMessage(SALE_ID);

        // capturamos os argumentos que foram enviados para o sqsTemplate
        ArgumentCaptor<String> queueCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(sqsTemplate, times(1))
                .send(queueCaptor.capture(), messageCaptor.capture());

        assertEquals(QUEUE_NAME, queueCaptor.getValue(), "A fila deve ser 'sale-activation-queue'");
        assertEquals(SALE_ID, messageCaptor.getValue(), "O saleId enviado deve ser o correto");
    }

    @Test
    void shouldLogAndSendWithoutExceptions() {
        // apenas garantimos que não lança exceção
        assertDoesNotThrow(() -> producerAdapter.sendSaleActivatedMessage(SALE_ID));
    }
}
