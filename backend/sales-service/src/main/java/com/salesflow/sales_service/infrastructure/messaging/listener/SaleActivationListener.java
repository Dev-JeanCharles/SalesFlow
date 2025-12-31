package com.salesflow.sales_service.infrastructure.messaging.listener;

import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import io.awspring.cloud.sqs.annotation.SqsListener;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SaleActivationListener {

    private static final Logger log = LoggerFactory.getLogger(SaleActivationListener.class);

    private static final String QUEUE_NAME = "sale-activation-queue";

    private final SaleRepositoryPort saleRepository;

    public SaleActivationListener(SaleRepositoryPort saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Transactional
    @SqsListener(QUEUE_NAME)
    public void activateSale(String saleId) {

        log.info(
                "[SQS][LISTENER] Message received | queue={} | saleId={}",
                QUEUE_NAME,
                saleId
        );

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> {
                    log.warn(
                            "[SQS][LISTENER] Sale not found | queue={} | saleId={}",
                            QUEUE_NAME,
                            saleId
                    );
                    return new IllegalStateException("Sale not found");
                });

        log.debug(
                "[SQS][LISTENER] Activating sale | saleId={}",
                saleId
        );

        sale.activate();

        saleRepository.save(sale);

        log.info(
                "[SQS][LISTENER] Sale activated successfully | saleId={}",
                saleId
        );
    }
}
