package com.salesflow.sales_service.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.salesflow.sales_service.application.exceptions.types.ProposalNotAllowedException;
import com.salesflow.sales_service.application.port.in.CreateSalesUseCase;
import com.salesflow.sales_service.infrastructure.exception.GlobalExceptionHandler;
import com.salesflow.sales_service.mock.SaleRequestMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SaleControllerTest {

    private MockMvc mockMvc;
    private CreateSalesUseCase createSalesUseCase;
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {

        createSalesUseCase = Mockito.mock(CreateSalesUseCase.class);

        mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new SaleController(createSalesUseCase))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
                .build();
    }

    @Test
    void shouldCreateSaleSuccessfully() throws Exception {

        doNothing().when(createSalesUseCase).createSale(any());

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SaleRequestMock.valid())
                )
                .andExpect(status().isCreated());

        verify(createSalesUseCase).createSale(any());
    }

    @Test
    void shouldReturnBadRequestWhenTaxIdentifierIsMissing() throws Exception {

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SaleRequestMock.missingTaxIdentifier())
                )
                .andExpect(status().isBadRequest());

        verify(createSalesUseCase, never()).createSale(any());
    }


    @Test
    void shouldReturnBadRequestWhenBillingDayIsInvalid() throws Exception {

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SaleRequestMock.invalidBillingDay())
                )
                .andExpect(status().isBadRequest());

        verify(createSalesUseCase, never()).createSale(any());
    }


    @Test
    void shouldReturnBadRequestWhenBusinessRuleFails() throws Exception {

        doThrow(new ProposalNotAllowedException("Erro de negócio"))
                .when(createSalesUseCase)
                .createSale(any());

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SaleRequestMock.valid())
                )
                .andExpect(status().isNotFound());

        verify(createSalesUseCase).createSale(any());
    }

}
