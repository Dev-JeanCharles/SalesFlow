package com.salesflow.sales_service.infrastructure.persistence.adapter;

import com.salesflow.sales_service.domain.enums.StatusPaymentEnum;
import com.salesflow.sales_service.domain.model.BillingHistory;
import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import com.salesflow.sales_service.infrastructure.persistence.entity.BillingHistoryJpa;
import com.salesflow.sales_service.infrastructure.persistence.entity.SaleJpa;
import com.salesflow.sales_service.infrastructure.persistence.repository.SaleJpaRepository;
import com.salesflow.sales_service.domain.enums.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SaleRepositoryAdapter implements SaleRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(SaleRepositoryAdapter.class);

    private final SaleJpaRepository repository;

    public SaleRepositoryAdapter(SaleJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sale save(Sale sale) {

        log.debug(
                "[REPOSITORY][SALE] Saving sale | saleId={} | status={}",
                sale.getSaleId(),
                sale.getStatus()
        );

        SaleJpa jpa = toJpa(sale);
        SaleJpa saved = repository.save(jpa);

        log.info(
                "[REPOSITORY][SALE] Sale saved successfully | saleId={} | status={}",
                saved.getSaleId(),
                saved.getStatus()
        );

        return toDomain(saved);
    }

    @Override
    public Optional<Sale> findById(String saleId) {

        log.debug(
                "[REPOSITORY][SALE] Searching sale by id | saleId={}",
                saleId
        );

        Optional<Sale> sale = repository.findById(saleId)
                .map(this::toDomain);

        if (sale.isPresent()) {
            log.debug(
                    "[REPOSITORY][SALE] Sale found | saleId={} | status={}",
                    saleId,
                    sale.get().getStatus()
            );
        } else {
            log.warn(
                    "[REPOSITORY][SALE] Sale not found | saleId={}",
                    saleId
            );
        }

        return sale;
    }

    @Override
    public boolean existsActiveSaleByTaxIdentifier(String taxIdentifier) {

        log.debug(
                "[REPOSITORY][SALE] Checking active sale by taxIdentifier={}",
                taxIdentifier
        );

        boolean exists = repository.existsByTaxIdentifierAndStatus(
                taxIdentifier,
                StatusEnum.ACTIVE
        );

        log.debug(
                "[REPOSITORY][SALE] Active sale exists={} | taxIdentifier={}",
                exists,
                taxIdentifier
        );

        return exists;
    }

    @Override
    public List<Sale> findPendingSales() {

        log.debug(
                "[REPOSITORY][SALE] Searching pending sales"
        );

        List<Sale> sales = repository.findByStatus(StatusEnum.PENDING)
                .stream()
                .map(this::toDomain)
                .toList();

        log.info(
                "[REPOSITORY][SALE] Pending sales found | total={}",
                sales.size()
        );

        return sales;
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
                billing.getInstallmentNumber(),
                billing.getPaymentValue(),
                billing.getPaymentStatus(),
                billing.getDueDate(),
                billing.getPaidAt(),
                billing.getPaymentMethod()
        );
    }

    private BillingHistory toDomain(BillingHistoryJpa jpa) {

        BillingHistory billing = new BillingHistory(
                jpa.getPaymentId(),
                jpa.getInstallmentNumber(),
                jpa.getPaymentValue(),
                jpa.getDueDate(),
                jpa.getPaymentMethod()
        );

        if (jpa.getPaymentStatus() == StatusPaymentEnum.PAID) {
            billing.markAsPaid(jpa.getPaidAt());
        }

        return billing;
    }
}
