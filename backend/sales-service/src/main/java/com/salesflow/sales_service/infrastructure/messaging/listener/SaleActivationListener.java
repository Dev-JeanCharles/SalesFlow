package com.salesflow.sales_service.infrastructure.messaging.listener;

import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import io.awspring.cloud.sqs.annotation.SqsListener;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SaleActivationListener {

    private final SaleRepositoryPort saleRepository;

    public SaleActivationListener(SaleRepositoryPort saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Transactional
    @SqsListener("sale-activation-queue")
    public void activateSale(String saleId) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new IllegalStateException("Sale not found"));

        sale.activate();

        saleRepository.save(sale);
    }
}

