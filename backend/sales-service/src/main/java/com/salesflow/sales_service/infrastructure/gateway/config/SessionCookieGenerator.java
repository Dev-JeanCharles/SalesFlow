package com.salesflow.sales_service.infrastructure.gateway.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SessionCookieGenerator {

    public String generate() {
        return "JSESSIONID=" + UUID.randomUUID();
    }
}
