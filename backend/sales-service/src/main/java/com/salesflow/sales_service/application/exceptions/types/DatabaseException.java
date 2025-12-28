package com.salesflow.sales_service.application.exceptions.types;

import com.salesflow.sales_service.application.exceptions.BusinessException;
public class DatabaseException extends BusinessException {
    public DatabaseException(String message) {
        super("DATABASE_ERROR", message);
    }
}