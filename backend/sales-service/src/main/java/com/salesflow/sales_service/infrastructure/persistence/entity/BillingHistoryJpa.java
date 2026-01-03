package com.salesflow.sales_service.infrastructure.persistence.entity;

import com.salesflow.sales_service.domain.enums.StatusPaymentEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sale_billing_history")
@Getter
@NoArgsConstructor
public class BillingHistoryJpa {

    @Id
    private String paymentId;

    @Column(nullable = false)
    private Integer installmentNumber;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPaymentEnum paymentStatus;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column
    private LocalDateTime paidAt;

    @Column
    private String paymentMethod;

    public BillingHistoryJpa(
            String paymentId,
            Integer installmentNumber,
            BigDecimal paymentValue,
            StatusPaymentEnum paymentStatus,
            LocalDateTime dueDate,
            LocalDateTime paidAt,
            String paymentMethod
    ) {
        this.paymentId = paymentId;
        this.installmentNumber = installmentNumber;
        this.paymentValue = paymentValue;
        this.paymentStatus = paymentStatus;
        this.dueDate = dueDate;
        this.paidAt = paidAt;
        this.paymentMethod = paymentMethod;
    }
}
