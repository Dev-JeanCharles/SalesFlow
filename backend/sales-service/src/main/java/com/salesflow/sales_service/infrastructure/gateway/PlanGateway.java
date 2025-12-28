package com.salesflow.sales_service.infrastructure.gateway;

import com.salesflow.sales_service.infrastructure.gateway.dto.PlanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "planClient", url = "http://localhost:7070")
public interface PlanGateway {
    @GetMapping("/plans")
    PlanDto getPlan(
            @RequestParam("plan_id") String planId,
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("Cookie") String cookie
    );
}