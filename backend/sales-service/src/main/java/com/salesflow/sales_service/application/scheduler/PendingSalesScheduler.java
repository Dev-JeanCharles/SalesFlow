package com.salesflow.sales_service.application.scheduler;

import com.salesflow.sales_service.application.port.out.SqsProducerPort;
import com.salesflow.sales_service.domain.enums.StatusPaymentEnum;
import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingSalesScheduler {

    private final SaleRepositoryPort saleRepository;
    private final SqsProducerPort sqsProducer;

    public PendingSalesScheduler(
            SaleRepositoryPort saleRepository,
            SqsProducerPort sqsProducer
    ) {
        this.saleRepository = saleRepository;
        this.sqsProducer = sqsProducer;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void processPendingSales() {

        List<Sale> pendingSales = saleRepository.findPendingSales();

        pendingSales.stream()
                .filter(this::firstPaymentIsPaid)
                .forEach(sale ->
                        sqsProducer.sendSaleActivatedMessage(sale.getSaleId())
                );
    }

    private boolean firstPaymentIsPaid(Sale sale) {
        return sale.getBillingHistory().stream()
                .findFirst()
                .map(b -> b.getPaymentStatus() == StatusPaymentEnum.PAID)
                .orElse(false);
    }
}
