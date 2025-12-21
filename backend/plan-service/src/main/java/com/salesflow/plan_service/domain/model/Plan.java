package com.salesflow.plan_service.domain.model;

import com.salesflow.plan_service.domain.enums.TypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Plan {

    private String planId; // 8 caracteres
    private String name;
    private TypeEnum type;
    private BigDecimal monthlyPrice;
    private LocalDateTime created;
    private boolean active;
    private String description;

    public Plan(String planId, String name, TypeEnum type, BigDecimal monthlyPrice, LocalDateTime created, boolean active, String description) {
        this.planId = planId;
        this.name = name;
        this.type = type;
        this.monthlyPrice = monthlyPrice;
        this.created = created;
        this.active = active;
        this.description = description;
    }

    public String getPlanId() { return planId; }
    public String getName() { return name; }
    public TypeEnum getType() { return type; }
    public BigDecimal getMonthlyPrice() { return monthlyPrice; }
    public LocalDateTime getCreated() { return created; }
    public boolean isActive() { return active; }
    public String getDescription() { return description; }

    public void setActive(boolean active) { this.active = active; }
}
