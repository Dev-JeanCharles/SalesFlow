package com.salesflow.sales_service.infrastructure.persistence.generator;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlanIdGenerator {

    private final JdbcTemplate jdbcTemplate;

    public PlanIdGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String nextId() {
        Long seq = jdbcTemplate.queryForObject(
                "SELECT nextval('plan_seq')",
                Long.class
        );

        return String.format("PL%06d", seq);
    }
}