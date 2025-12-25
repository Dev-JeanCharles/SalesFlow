package com.salesflow.plan_service.application.exception.types;

import com.salesflow.plan_service.application.exception.BusinessException;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message, String planoNãoEncontrado) {
        super("NOT_FOUND", message);
    }
}
