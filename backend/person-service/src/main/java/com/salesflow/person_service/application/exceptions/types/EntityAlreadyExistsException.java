package com.salesflow.person_service.application.exceptions.types;

import com.salesflow.person_service.application.exceptions.BusinessException;

public class EntityAlreadyExistsException extends BusinessException {

    public EntityAlreadyExistsException(String message) {
        super("ENTITY_ALREADY_EXISTS", message);
    }
}