package com.salesflow.sales_service.domain.model;

import com.salesflow.sales_service.domain.enums.StatusPaymentEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BillingHistory {

    private final String paymentId;
    private final BigDecimal paymentValue;
    private final StatusPaymentEnum paymentStatus;
    private final LocalDateTime paymentDate;
    private final String paymentMethod;
}
