package com.salesflow.sales_service.infrastructure.controller;

import com.salesflow.sales_service.application.dto.SaleRequestDto;
import com.salesflow.sales_service.application.port.in.CreateSalesUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private static final Logger log = LoggerFactory.getLogger(SaleController.class);

    private final CreateSalesUseCase createSalesUseCase;

    public SaleController(CreateSalesUseCase createSalesUseCase) {
        this.createSalesUseCase = createSalesUseCase;
    }

    @PostMapping
    public ResponseEntity<String> createSale(@RequestBody SaleRequestDto saleRequestDto) {

        log.info(
                "[CONTROLLER][SALE] Create sale request received | taxIdentifier={}",
                saleRequestDto.getTaxIdentifier()
        );

        createSalesUseCase.createSale(saleRequestDto);

        log.info(
                "[CONTROLLER][SALE] Sale created successfully | taxIdentifier={}",
                saleRequestDto.getTaxIdentifier()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Venda criada com sucesso para: " + saleRequestDto.getTaxIdentifier());
    }
}
