package com.salesflow.plan_service.application.exception.types;

import com.salesflow.plan_service.application.exception.BusinessException;

public class UnprocessableEntityException extends BusinessException {
    public UnprocessableEntityException(String message) {
        super("UNPROCESSABLE_ENTITY", message);
    }
}