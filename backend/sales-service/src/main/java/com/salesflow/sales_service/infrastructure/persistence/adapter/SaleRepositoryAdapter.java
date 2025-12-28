package com.salesflow.sales_service.infrastructure.persistence.adapter;


import com.salesflow.sales_service.domain.model.BillingHistory;
import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import com.salesflow.sales_service.infrastructure.persistence.entity.BillingHistoryJpa;
import com.salesflow.sales_service.infrastructure.persistence.entity.SaleJpa;
import com.salesflow.sales_service.infrastructure.persistence.repository.SaleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class SaleRepositoryAdapter implements SaleRepositoryPort {

    private final SaleJpaRepository repository;

    public SaleRepositoryAdapter(SaleJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sale save(Sale sale) {
        SaleJpa jpa = toJpa(sale);
        SaleJpa saved = repository.save(jpa);
        return toDomain(saved);
    }

    @Override
    public Optional<Sale> findById(String saleId) {
        return repository.findById(saleId)
                .map(this::toDomain);
    }

    /* ======================
       Mappers
       ====================== */

    private SaleJpa toJpa(Sale sale) {
        return new SaleJpa(
                sale.getSaleId(),
                sale.getTaxIdentifier(),
                sale.getPlanId(),
                sale.getMonthlyPrice(),
                sale.getPersonName(),
                sale.getPlanName(),
                sale.getPlanType(),
                sale.getStartDate(),
                sale.getEndDate(),
                sale.getBillingDay(),
                sale.getDiscount(),
                sale.getFinalMonthlyPrice(),
                sale.getStatus(),
                sale.getCreatedAt(),
                sale.getCanceledAt(),
                sale.getBillingHistory()
                        .stream()
                        .map(this::toJpa)
                        .toList()

        );
    }

    private Sale toDomain(SaleJpa jpa) {
        return Sale.restore(
                jpa.getSaleId(),
                jpa.getTaxIdentifier(),
                jpa.getPlanId(),
                jpa.getPersonName(),
                jpa.getPlanName(),
                jpa.getPlanType(),
                jpa.getMonthlyPrice(),
                jpa.getDiscount(),
                jpa.getFinalMonthlyPrice(),
                jpa.getStartDate(),
                jpa.getEndDate(),
                jpa.getBillingDay(),
                jpa.getStatus(),
                jpa.getCreatedAt(),
                jpa.getCanceledAt(),
                jpa.getBillingHistory()
                        .stream()
                        .map(this::toDomain)
                        .toList()
        );
    }

    private BillingHistoryJpa toJpa(BillingHistory billing) {
        return new BillingHistoryJpa(
                billing.getPaymentId(),
                billing.getPaymentValue(),
                billing.getPaymentStatus(),
                billing.getPaymentDate(),
                billing.getPaymentMethod()
        );
    }

    private BillingHistory toDomain(BillingHistoryJpa jpa) {
        return new BillingHistory(
                jpa.getPaymentId(),
                jpa.getPaymentValue(),
                jpa.getPaymentStatus(),
                jpa.getPaymentDate(),
                jpa.getPaymentMethod()
        );
    }

}
