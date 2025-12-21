package com.salesflow.plan_service.infrastructure.persistence.init;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("""
            CREATE SEQUENCE IF NOT EXISTS plan_seq
            START WITH 1
            INCREMENT BY 1
        """);
    }
}
