package com.salesflow.sales_service.infrastructure.controller;

import com.salesflow.sales_service.application.dto.SaleRequestDto;
import com.salesflow.sales_service.application.port.in.CreateSalesUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final CreateSalesUseCase createSalesUseCase;

    public SaleController(
            CreateSalesUseCase createSalesUseCase
    ) {
        this.createSalesUseCase = createSalesUseCase;
    }
    @PostMapping
    public ResponseEntity<String> createSale(@RequestBody SaleRequestDto saleRequestDto) {
        System.out.println("Recebido saleDto: " + saleRequestDto.getTaxIdentifier());

        createSalesUseCase.createPlan(saleRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Venda criada com sucesso para: " + saleRequestDto.getTaxIdentifier());
    }
}