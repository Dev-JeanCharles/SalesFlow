package com.salesflow.sales_service.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class SaleRequestDto {

    @NotNull
    @JsonProperty("tax_identifier")
    private String taxIdentifier;

    @JsonProperty("plan_id")
    private String planId;

    @Min(1)
    @Max(28)
    @JsonProperty("billing_day")
    private Integer billingDay;

    @JsonProperty("discount")
    private BigDecimal discount;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("canceled_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime canceledAt;

    @JsonProperty("billing")
    private BillingRequestDto billing;

}
