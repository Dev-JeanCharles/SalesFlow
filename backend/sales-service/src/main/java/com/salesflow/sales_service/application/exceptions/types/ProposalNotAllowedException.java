package com.salesflow.sales_service.application.exceptions.types;


import com.salesflow.sales_service.application.exceptions.BusinessException;

public class ProposalNotAllowedException extends BusinessException {

    public ProposalNotAllowedException(String message) {
        super("PROPOSAL_NOT_ALLOWED", message);
    }
}
