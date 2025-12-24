package com.salesflow.plan_service.application.exception.types;

public class InvalidPlanTypeException extends RuntimeException {

    public InvalidPlanTypeException(String message) {
        super(message);
    }
}