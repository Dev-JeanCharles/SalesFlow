package com.salesflow.sales_service.application.port.in;

import com.salesflow.sales_service.application.dto.SaleRequestDto;

public interface CreateSalesUseCase {
    void createSale(SaleRequestDto request);
}