package com.salesflow.person_service.infrastructure.persistence.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonIdGenerator {

    private final JdbcTemplate jdbcTemplate;

    public String nextId() {
        Long seq = jdbcTemplate.queryForObject(
                "SELECT nextval('plan_seq')",
                Long.class
        );

        return String.format("PL%06d", seq);
    }
}
