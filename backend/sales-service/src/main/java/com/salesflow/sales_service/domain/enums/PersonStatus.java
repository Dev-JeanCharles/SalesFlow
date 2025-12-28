package com.salesflow.sales_service.domain.enums;

public enum PersonStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED;

    public static PersonStatus from(String value) {

        try {
            return PersonStatus.valueOf(value.toUpperCase());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Status inválido: " + value);
        }
    }
}


