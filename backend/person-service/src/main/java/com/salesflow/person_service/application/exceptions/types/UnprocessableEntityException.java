package com.salesflow.person_service.application.exceptions.types;

import com.salesflow.person_service.application.exceptions.BusinessException;

public class UnprocessableEntityException extends BusinessException {
    public UnprocessableEntityException(String message) {
        super("UNPROCESSABLE_ENTITY", message);
    }
}