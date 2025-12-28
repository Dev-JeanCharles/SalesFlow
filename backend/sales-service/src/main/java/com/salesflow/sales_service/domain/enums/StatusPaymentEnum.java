package com.salesflow.sales_service.domain.enums;

public enum StatusPaymentEnum {

    PENDING,   // Pagamento criado e aguardando processamento
    PAID,      // Pagamento realizado com sucesso
    FAILED,    // Falha no processamento
    CANCELED   // Pagamento cancelado
}