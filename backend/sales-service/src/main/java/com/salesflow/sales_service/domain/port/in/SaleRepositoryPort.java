package com.salesflow.sales_service.domain.port.in;

import com.salesflow.sales_service.domain.enums.StatusEnum;
import com.salesflow.sales_service.domain.model.Sale;

import java.util.Optional;

public interface SaleRepositoryPort {

    Sale save(Sale sale);

    Optional<Sale> findById(String saleId);

    boolean existsActiveSaleByTaxIdentifier(String taxIdentifier);
}