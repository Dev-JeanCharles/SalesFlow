package com.salesflow.plan_service.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.salesflow.plan_service.application.exception.types.InvalidPlanTypeException;

public enum TypeEnum {

    PRE_PAGO,
    CONTROLE,
    POS_PAGO;
    @JsonCreator
    public static TypeEnum from(String value) {
        if (value == null) {
            return null;
        }

        try {
            return TypeEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidPlanTypeException(
                    "Tipo de plano inválido: " + value
            );
        }
    }
}
