package com.salesflow.sales_service.mock;

import com.salesflow.sales_service.application.dto.BillingRequestDto;
import com.salesflow.sales_service.application.dto.SaleRequestDto;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

public class SaleRequestMock {

    /* ======================
    CENÁRIO: SUCESSO
    ====================== */
    public static String valid() {
        return """
            {
              "tax_identifier": "12345678900",
              "plan_id": "PLAN001",
              "billing_day": 10,
              "discount": 10,
              "billing": {
                "payment_method": "CREDIT_CARD"
              }
            }
        """;
    }

    /* ======================
       CENÁRIO: SEM CAMPO OBRIGATÓRIO
       ====================== */
    public static String missingTaxIdentifier() {
        return """
            {
              "plan_id": "PLAN001",
              "billing_day": 10,
              "discount": 10,
              "billing": {
                "payment_method": "CREDIT_CARD"
              }
            }
        """;
    }

    /* ======================
       CENÁRIO: CAMPO MAL FORMATADO
       ====================== */
    public static String invalidBillingDay() {
        return """
            {
              "tax_identifier": "12345678900",
              "plan_id": "PLAN001",
              "billing_day": 35,
              "discount": 10,
              "billing": {
                "payment_method": "CREDIT_CARD"
              }
            }
        """;
    }

    public static SaleRequestDto validService() {
        SaleRequestDto dto = new SaleRequestDto();

        ReflectionTestUtils.setField(dto, "taxIdentifier", "12345678900");
        ReflectionTestUtils.setField(dto, "planId", "PLAN001");
        ReflectionTestUtils.setField(dto, "billingDay", 10);
        ReflectionTestUtils.setField(dto, "discount", BigDecimal.TEN);

        BillingRequestDto billing = new BillingRequestDto();
        ReflectionTestUtils.setField(billing, "paymentMethod", "CREDIT_CARD");

        ReflectionTestUtils.setField(dto, "billing", billing);

        return dto;
    }

}