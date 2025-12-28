package com.salesflow.sales_service.domain.models;

import com.salesflow.sales_service.domain.enums.SaleStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Sale {
    private String saleId;
    private String taxIdentifier;
    private String planId;
    private BigDecimal monthlyPrice;
    private String personName;
    private String planName;
    private String planType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer billingDay;
    private BigDecimal discount;
    private BigDecimal finalMonthlyPrice;
    private SaleStatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime canceledAt;
    private Payment billing;
}
