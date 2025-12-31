package com.salesflow.sales_service.infrastructure.messaging.sqs.adapter;
import com.salesflow.sales_service.application.port.out.SqsProducerPort;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SqsProducerAdapter implements SqsProducerPort {

    private static final Logger log = LoggerFactory.getLogger(SqsProducerAdapter.class);

    private static final String QUEUE_NAME = "sale-activation-queue";

    private final SqsTemplate sqsTemplate;

    public SqsProducerAdapter(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    public void sendSaleActivatedMessage(String saleId) {

        log.info(
                "[SQS][PRODUCER] Sending sale activation message | queue={} | saleId={}",
                QUEUE_NAME,
                saleId
        );

        sqsTemplate.send(QUEUE_NAME, saleId);

        log.debug(
                "[SQS][PRODUCER] Message sent successfully | queue={} | saleId={}",
                QUEUE_NAME,
                saleId
        );
    }
}
