package com.salesflow.sales_service.infrastructure.persistence.repository;

import com.salesflow.sales_service.infrastructure.persistence.entity.SaleJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleJpaRepository extends JpaRepository<SaleJpa, String> {}