package com.salesflow.person_service.domain.enums;

public enum PersonStatusEnum {
    ACTIVE,
    INACTIVE,
    BLOCKED;
    public static PersonStatusEnum from(String value) {

        try {
            return PersonStatusEnum.valueOf(value.toUpperCase());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Status inválido: " + value);
        }
    }

}
