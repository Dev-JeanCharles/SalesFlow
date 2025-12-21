package com.salesflow.plan_service.application.exception.types;

import com.salesflow.plan_service.application.exception.BusinessException;

public class DatabaseException extends BusinessException {
    public DatabaseException(String message) {
        super("DATABASE_ERROR", message);
    }
}