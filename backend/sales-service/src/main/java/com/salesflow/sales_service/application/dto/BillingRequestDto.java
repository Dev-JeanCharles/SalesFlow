package com.salesflow.sales_service.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BillingRequestDto {

    @JsonProperty("next_billing_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime nextBillingDate;

    @JsonProperty("payment_method")
    private String paymentMethod;

}