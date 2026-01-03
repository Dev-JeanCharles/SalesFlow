package com.salesflow.sales_service.application.scheduler;

import com.salesflow.sales_service.application.port.out.SqsProducerPort;
import com.salesflow.sales_service.domain.enums.StatusPaymentEnum;
import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingSalesScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(PendingSalesScheduler.class);
    private final SaleRepositoryPort saleRepository;
    private final SqsProducerPort sqsProducer;
    private final Counter salesActivatedCounter;
    public PendingSalesScheduler(
            SaleRepositoryPort saleRepository,
            SqsProducerPort sqsProducer,
            MeterRegistry meterRegistry
    ) {
        this.saleRepository = saleRepository;
        this.sqsProducer = sqsProducer;
        this.salesActivatedCounter = Counter.builder("sales_contracts_activated_total")
                .description("Total of sales contracts activated and sent to SQS")
                .tag("application", "sales-service")
                .register(meterRegistry);
    }
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void processPendingSales() {

        log.info("[JOB][PENDING_SALES] Starting pending sales processing");

        List<Sale> pendingSales = saleRepository.findPendingSales();

        log.info(
                "[JOB][PENDING_SALES] Found {} pending sales",
                pendingSales.size()
        );

        pendingSales.forEach(sale -> {
            try {
                log.debug(
                        "[JOB][PENDING_SALES] Checking saleId={}",
                        sale.getSaleId()
                );

                if (firstPaymentIsPaid(sale)) {
                    sqsProducer.sendSaleActivatedMessage(sale.getSaleId());

                    salesActivatedCounter.increment();

                    log.info(
                            "[JOB][PENDING_SALES] Activation message sent | saleId={}",
                            sale.getSaleId()
                    );
                }
            } catch (Exception ex) {
                log.error(
                        "[JOB][PENDING_SALES] Error processing saleId={}",
                        sale.getSaleId(),
                        ex
                );
            }
        });

        log.info("[JOB][PENDING_SALES] Processing finished");
    }

    private boolean firstPaymentIsPaid(Sale sale) {
        return sale.getBillingHistory().stream()
                .filter(b -> b.getInstallmentNumber() == 1)
                .findFirst()
                .map(b -> b.getPaymentStatus() == StatusPaymentEnum.PAID)
                .orElse(false);
    }

}
