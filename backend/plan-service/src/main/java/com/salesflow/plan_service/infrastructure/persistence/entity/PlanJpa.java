package com.salesflow.plan_service.infrastructure.persistence.entity;

import com.salesflow.plan_service.domain.enums.TypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "plans")
public class PlanJpa {

    @Id
    @Column(length = 10)
    private String planId;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEnum type;

    private BigDecimal monthlyPrice;

    private LocalDateTime created;

    private boolean active;

    private String description;

    public PlanJpa() {}

    public PlanJpa(String planId, String name, TypeEnum type, BigDecimal monthlyPrice, LocalDateTime created, boolean active, String description) {
        this.planId = planId;
        this.name = name;
        this.type = type;
        this.monthlyPrice = monthlyPrice;
        this.created = created;
        this.active = active;
        this.description = description;
    }

    // Getters e setters...
    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public TypeEnum getType() { return type; }
    public void setType(TypeEnum type) { this.type = type; }
    public BigDecimal getMonthlyPrice() { return monthlyPrice; }
    public void setMonthlyPrice(BigDecimal monthlyPrice) { this.monthlyPrice = monthlyPrice; }
    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}