package com.salesflow.person_service.application.exceptions.types;

import com.salesflow.person_service.application.exceptions.BusinessException;

public class DatabaseException extends BusinessException {
    public DatabaseException(String message) {
        super("DATABASE_ERROR", message);
    }
}