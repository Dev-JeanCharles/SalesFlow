package com.salesflow.sales_service.domain.models;

import com.salesflow.sales_service.domain.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {
    private String paymentId;
    private BigDecimal paymentValue;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
}
