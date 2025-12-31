package com.salesflow.sales_service.application.port.out;

public interface SqsProducerPort {
    void sendSaleActivatedMessage(String saleId);
}
