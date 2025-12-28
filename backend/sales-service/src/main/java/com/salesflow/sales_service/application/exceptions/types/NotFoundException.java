package com.salesflow.sales_service.application.exceptions.types;


import com.salesflow.sales_service.application.exceptions.BusinessException;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super("NOT_FOUND", message);
    }
}
