package com.salesflow.sales_service.mock;

import com.salesflow.sales_service.domain.model.BillingHistory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BillingHistoryMock {

    /* ======================
       1ª PARCELA PAGA
       ====================== */
    public static List<BillingHistory> paidFirst() {
        BillingHistory first = installment(1);
        first.markAsPaid(LocalDateTime.now());

        return List.of(first);
    }

    /* ======================
       1ª PARCELA PENDENTE
       ====================== */
    public static List<BillingHistory> pendingFirst() {
        return List.of(
                installment(1)
        );
    }

    /* ======================
       SEM 1ª PARCELA
       ====================== */
    public static List<BillingHistory> withoutFirst() {
        BillingHistory second = installment(2);
        second.markAsPaid(LocalDateTime.now());

        return List.of(second);
    }

    /* ======================
       VÁRIAS PARCELAS (1ª PAGA)
       ====================== */
    public static List<BillingHistory> multipleWithFirstPaid() {

        BillingHistory first = installment(1);
        first.markAsPaid(LocalDateTime.now());

        return List.of(
                first,
                installment(2),
                installment(3)
        );
    }

    /* ======================
       Factory base
       ====================== */
    private static BillingHistory installment(int number) {
        return new BillingHistory(
                "PAY-" + number,
                number,
                BigDecimal.valueOf(100),
                LocalDateTime.now().plusDays(number * 30L),
                "CREDIT_CARD"
        );
    }
}
