package com.salesflow.sales_service.infrastructure.persistence.adapter;
import com.salesflow.sales_service.domain.enums.StatusEnum;
import com.salesflow.sales_service.domain.model.Sale;
import com.salesflow.sales_service.infrastructure.persistence.entity.SaleJpa;
import com.salesflow.sales_service.infrastructure.persistence.repository.SaleJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaleRepositoryAdapterTest {

    @Mock
    private SaleJpaRepository repository;

    @InjectMocks
    private SaleRepositoryAdapter adapter;

    private Sale sale;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sale = Sale.create(
                "SALE001",
                "12345678901",
                "PLAN001",
                "John Doe",
                "Premium Plan",
                "Type A",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(10),
                LocalDateTime.now(),
                5,
                List.of()
        );
    }

    @Test
    void shouldSaveSale() {
        // dado que o repository retorna a mesma entidade JPA
        when(repository.save(any(SaleJpa.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Sale saved = adapter.save(sale);

        assertNotNull(saved);
        assertEquals(sale.getSaleId(), saved.getSaleId());
        verify(repository, times(1)).save(any(SaleJpa.class));
    }

    @Test
    void shouldFindById() {
        SaleJpa jpa = new SaleJpa(
                sale.getSaleId(),
                sale.getTaxIdentifier(),
                sale.getPlanId(),
                sale.getMonthlyPrice(),
                sale.getPersonName(),
                sale.getPlanName(),
                sale.getPlanType(),
                sale.getStartDate(),
                sale.getEndDate(),
                sale.getBillingDay(),
                sale.getDiscount(),
                sale.getFinalMonthlyPrice(),
                sale.getStatus(),
                sale.getCreatedAt(),
                sale.getCanceledAt(),
                List.of()
        );

        when(repository.findById(sale.getSaleId()))
                .thenReturn(Optional.of(jpa));

        Optional<Sale> found = adapter.findById(sale.getSaleId());

        assertTrue(found.isPresent());
        assertEquals(sale.getSaleId(), found.get().getSaleId());
        verify(repository, times(1)).findById(sale.getSaleId());
    }

    @Test
    void shouldReturnEmptyWhenSaleNotFound() {
        when(repository.findById("UNKNOWN")).thenReturn(Optional.empty());

        Optional<Sale> found = adapter.findById("UNKNOWN");

        assertTrue(found.isEmpty());
        verify(repository, times(1)).findById("UNKNOWN");
    }

    @Test
    void shouldCheckExistsActiveSaleByTaxIdentifier() {
        when(repository.existsByTaxIdentifierAndStatus(sale.getTaxIdentifier(), StatusEnum.ACTIVE))
                .thenReturn(true);

        boolean exists = adapter.existsActiveSaleByTaxIdentifier(sale.getTaxIdentifier());

        assertTrue(exists);
        verify(repository, times(1))
                .existsByTaxIdentifierAndStatus(sale.getTaxIdentifier(), StatusEnum.ACTIVE);
    }

    @Test
    void shouldFindPendingSales() {
        SaleJpa jpa = new SaleJpa(
                sale.getSaleId(),
                sale.getTaxIdentifier(),
                sale.getPlanId(),
                sale.getMonthlyPrice(),
                sale.getPersonName(),
                sale.getPlanName(),
                sale.getPlanType(),
                sale.getStartDate(),
                sale.getEndDate(),
                sale.getBillingDay(),
                sale.getDiscount(),
                sale.getFinalMonthlyPrice(),
                StatusEnum.PENDING,
                sale.getCreatedAt(),
                sale.getCanceledAt(),
                List.of()
        );

        when(repository.findByStatus(StatusEnum.PENDING))
                .thenReturn(List.of(jpa));

        List<Sale> pending = adapter.findPendingSales();

        assertEquals(1, pending.size());
        assertEquals(StatusEnum.PENDING, pending.get(0).getStatus());
        verify(repository, times(1)).findByStatus(StatusEnum.PENDING);
    }
}
