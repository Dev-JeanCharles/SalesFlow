package com.salesflow.sales_service.application.service;

import com.salesflow.sales_service.domain.model.BillingHistory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BillingScheduleService {

    private static final int CONTRACT_MONTHS = 12;

    public static List<BillingHistory> generate(
            BigDecimal monthlyAmount,
            LocalDateTime startDate,
            int billingDay,
            String paymentMethod
    ) {

        List<BillingHistory> billings = new ArrayList<>();

        for (int i = 1; i <= CONTRACT_MONTHS; i++) {

            LocalDateTime dueDate = startDate
                    .plusMonths(i - 1)
                    .withDayOfMonth(
                            Math.min(
                                    billingDay,
                                    startDate.toLocalDate()
                                            .plusMonths(i - 1)
                                            .lengthOfMonth()
                            )
                    );

            billings.add(new BillingHistory(
                    UUID.randomUUID().toString(),
                    i,
                    monthlyAmount,
                    dueDate,
                    paymentMethod
            ));
        }

        return billings;
    }
}
