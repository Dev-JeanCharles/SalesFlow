package com.salesflow.sales_service.infrastructure.persistence.repository;

import com.salesflow.sales_service.domain.enums.StatusEnum;
import com.salesflow.sales_service.infrastructure.persistence.entity.SaleJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SaleJpaRepository extends JpaRepository<SaleJpa, String> {
    boolean existsByTaxIdentifierAndStatus(
            String taxIdentifier,
            StatusEnum status
    );
    List<SaleJpa> findByStatus(StatusEnum status);
}