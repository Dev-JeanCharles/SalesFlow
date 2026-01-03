package com.salesflow.sales_service.domain.model;

import com.salesflow.sales_service.domain.enums.StatusPaymentEnum;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class BillingHistory {

    private final String paymentId;
    private final Integer installmentNumber;
    private final BigDecimal paymentValue;
    private final LocalDateTime dueDate;
    private final String paymentMethod;

    private StatusPaymentEnum paymentStatus;
    private LocalDateTime paidAt;

    public BillingHistory(
            String paymentId,
            Integer installmentNumber,
            BigDecimal paymentValue,
            LocalDateTime dueDate,
            String paymentMethod
    ) {
        if (installmentNumber == null || installmentNumber < 1) {
            throw new IllegalArgumentException("Installment number must be >= 1");
        }

        this.paymentId = paymentId;
        this.installmentNumber = installmentNumber;
        this.paymentValue = paymentValue;
        this.dueDate = dueDate;
        this.paymentMethod = paymentMethod;

        this.paymentStatus = StatusPaymentEnum.PENDING;
        this.paidAt = null;
    }

    /* =========
       Regras de negócio
       ========= */

    public boolean isFirstInstallment() {
        return installmentNumber == 1;
    }

    public boolean isPaid() {
        return paymentStatus == StatusPaymentEnum.PAID;
    }

    public void markAsPaid(LocalDateTime paidAt) {
        if (this.paymentStatus == StatusPaymentEnum.PAID) {
            throw new IllegalStateException("Installment already paid");
        }

        this.paymentStatus = StatusPaymentEnum.PAID;
        this.paidAt = paidAt;
    }
}
