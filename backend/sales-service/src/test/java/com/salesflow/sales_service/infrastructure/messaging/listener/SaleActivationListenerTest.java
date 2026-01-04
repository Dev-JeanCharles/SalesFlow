package com.salesflow.sales_service.infrastructure.messaging.listener;

import com.salesflow.sales_service.domain.enums.StatusEnum;
import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.domain.port.in.SaleRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleActivationListenerTest {

    private static final String SALE_ID = "SALE001";

    @Mock
    private SaleRepositoryPort saleRepository;

    @InjectMocks
    private SaleActivationListener listener;

    private Sale pendingSale;

    @BeforeEach
    void setup() {
        pendingSale = Sale.create(
                SALE_ID,
                "12345678900",
                "PLAN001",
                "João Silva",
                "Plano Gold",
                "PREMIUM",
                BigDecimal.valueOf(100),
                BigDecimal.ZERO,
                LocalDateTime.now(),
                10,
                List.of()
        );
    }

    /* =========================
       CENÁRIO 1
       Venda pendente → ativa
       ========================= */
    @Test
    void shouldActivateSaleWhenSaleExistsAndIsPending() {

        when(saleRepository.findById(SALE_ID))
                .thenReturn(Optional.of(pendingSale));

        listener.activateSale(SALE_ID);

        assertEquals(StatusEnum.ACTIVE, pendingSale.getStatus());

        verify(saleRepository).save(pendingSale);
    }

    /* =========================
       CENÁRIO 2
       Venda não encontrada
       ========================= */
    @Test
    void shouldThrowExceptionWhenSaleNotFound() {

        when(saleRepository.findById(SALE_ID))
                .thenReturn(Optional.empty());

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> listener.activateSale(SALE_ID)
                );

        assertEquals("Sale not found", exception.getMessage());

        verify(saleRepository, never()).save(any());
    }

    /* =========================
       CENÁRIO 3
       Venda não está PENDING
       ========================= */
    @Test
    void shouldThrowExceptionWhenSaleIsNotPending() {

        Sale activeSale = Sale.restore(
                SALE_ID,
                "12345678900",
                "PLAN001",
                "João Silva",
                "Plano Gold",
                "PREMIUM",
                BigDecimal.valueOf(100),
                BigDecimal.ZERO,
                BigDecimal.valueOf(100),
                LocalDateTime.now(),
                LocalDateTime.now().plusMonths(12),
                10,
                StatusEnum.ACTIVE,
                LocalDateTime.now(),
                null,
                List.of()
        );

        when(saleRepository.findById(SALE_ID))
                .thenReturn(Optional.of(activeSale));

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> listener.activateSale(SALE_ID)
                );

        assertEquals(
                "Only pending sales can be activated",
                exception.getMessage()
        );

        verify(saleRepository, never()).save(any());
    }
}
