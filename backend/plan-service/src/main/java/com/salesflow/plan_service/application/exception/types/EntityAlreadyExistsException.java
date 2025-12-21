package com.salesflow.plan_service.application.exception.types;

import com.salesflow.plan_service.application.exception.BusinessException;

public class EntityAlreadyExistsException extends BusinessException {

    public EntityAlreadyExistsException(String message) {
        super("ENTITY_ALREADY_EXISTS", message);
    }
}