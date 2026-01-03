package com.salesflow.sales_service.domain.model;

import com.salesflow.sales_service.domain.enums.StatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sale {

    private final String saleId;
    private final String taxIdentifier;
    private final String planId;

    private final String personName;
    private final String planName;
    private final String planType;

    private final BigDecimal monthlyPrice;
    private final BigDecimal discount;
    private final BigDecimal finalMonthlyPrice;

    private final LocalDateTime startDate;
    private LocalDateTime endDate;

    private final Integer billingDay;

    private StatusEnum status;

    private final LocalDateTime createdAt;
    private LocalDateTime canceledAt;


    private final List<BillingHistory> billingHistory;

    public static Sale create(
            String saleId,
            String taxIdentifier,
            String planId,
            String personName,
            String planName,
            String planType,
            BigDecimal monthlyPrice,
            BigDecimal discount,
            LocalDateTime startDate,
            Integer billingDay,
            List<BillingHistory> billingHistory
    ) {
        validateInputs(monthlyPrice, billingDay);

        return new Sale(
                saleId,
                taxIdentifier,
                planId,
                personName,
                planName,
                planType,
                monthlyPrice,
                discount != null ? discount : BigDecimal.ZERO,
                calculateFinalPrice(monthlyPrice, discount),
                startDate,
                startDate.plusMonths(12),
                billingDay,
                StatusEnum.PENDING,
                LocalDateTime.now(),
                null,
                billingHistory != null ? billingHistory : List.of()
        );
    }

    public static Sale restore(
            String saleId,
            String taxIdentifier,
            String planId,
            String personName,
            String planName,
            String planType,
            BigDecimal monthlyPrice,
            BigDecimal discount,
            BigDecimal finalMonthlyPrice,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer billingDay,
            StatusEnum status,
            LocalDateTime createdAt,
            LocalDateTime canceledAt,
            List<BillingHistory> billingHistory
    ) {
        return new Sale(
                saleId,
                taxIdentifier,
                planId,
                personName,
                planName,
                planType,
                monthlyPrice,
                discount,
                finalMonthlyPrice,
                startDate,
                endDate,
                billingDay,
                status,
                createdAt,
                canceledAt,
                billingHistory
        );
    }

    public void cancel(LocalDateTime canceledAt) {
        if (this.status != StatusEnum.ACTIVE) {
            throw new IllegalStateException("Somente vendas ativas podem ser canceladas");
        }

        this.status = StatusEnum.CANCELED;
        this.canceledAt = canceledAt;
        this.endDate = canceledAt;
    }

    private static void validateInputs(BigDecimal monthlyPrice, Integer billingDay) {
        if (monthlyPrice == null || monthlyPrice.signum() <= 0) {
            throw new IllegalArgumentException("Valor mensal deve ser maior que zero");
        }

        if (billingDay == null || billingDay < 1 || billingDay > 28) {
            throw new IllegalArgumentException("Dia de faturamento deve estar entre 1 e 28");
        }
    }

    private static BigDecimal calculateFinalPrice(
            BigDecimal monthlyPrice,
            BigDecimal discount
    ) {
        BigDecimal finalPrice = monthlyPrice.subtract(discount);
        return finalPrice.signum() < 0 ? BigDecimal.ZERO : finalPrice;
    }


    public void activate() {
        if (this.status != StatusEnum.PENDING) {
            throw new IllegalStateException("Only pending sales can be activated");
        }

        this.status = StatusEnum.ACTIVE;
    }
}
