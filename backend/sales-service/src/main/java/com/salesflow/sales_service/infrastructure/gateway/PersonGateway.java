package com.salesflow.sales_service.infrastructure.gateway;

import com.salesflow.sales_service.infrastructure.gateway.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "personClient", url = "http://localhost:7071")
public interface PersonGateway {
    @GetMapping("/person")
    PersonDto getPerson(
            @RequestParam("tax_identifier") String personId,
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("Cookie") String cookie
    );
}