package com.salesflow.sales_service.infrastructure.messaging.sqs.adapter;

import com.salesflow.sales_service.application.port.out.SqsProducerPort;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SqsProducerAdapter implements SqsProducerPort {

    private final SqsTemplate sqsTemplate;
    private final String queueName = "sale-activation-queue";

    public SqsProducerAdapter(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    public void sendSaleActivatedMessage(String saleId) {
        sqsTemplate.send(queueName, saleId);
    }
}
