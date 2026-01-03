package com.salesflow.sales_service.infrastructure.persistence.entity;

import com.salesflow.sales_service.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "sales")
public class SaleJpa {

    @Id
    @Column(length = 8, nullable = false)
    private String saleId;

    @Column(nullable = false)
    private String taxIdentifier;

    @Column(nullable = false)
    private String planId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyPrice;

    @Column(nullable = false)
    private String personName;

    @Column(nullable = false)
    private String planName;

    @Column(nullable = false)
    private String planType;

    @Column(nullable = false)
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(nullable = false)
    private Integer billingDay;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal finalMonthlyPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime canceledAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sale_id")
    private List<BillingHistoryJpa> billingHistory;

    protected SaleJpa() {
        // JPA only
    }

    public SaleJpa(
            String saleId,
            String taxIdentifier,
            String planId,
            BigDecimal monthlyPrice,
            String personName,
            String planName,
            String planType,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer billingDay,
            BigDecimal discount,
            BigDecimal finalMonthlyPrice,
            StatusEnum status,
            LocalDateTime createdAt,
            LocalDateTime canceledAt,
            List<BillingHistoryJpa> billingHistory
    ) {
        this.saleId = saleId;
        this.taxIdentifier = taxIdentifier;
        this.planId = planId;
        this.monthlyPrice = monthlyPrice;
        this.personName = personName;
        this.planName = planName;
        this.planType = planType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.billingDay = billingDay;
        this.discount = discount;
        this.finalMonthlyPrice = finalMonthlyPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.canceledAt = canceledAt;
        this.billingHistory = billingHistory;
    }

}
