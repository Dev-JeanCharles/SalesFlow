package com.salesflow.sales_service.infrastructure.gateway.adapter;

import com.salesflow.sales_service.application.port.out.PersonPort;
import com.salesflow.sales_service.infrastructure.gateway.PersonGateway;
import com.salesflow.sales_service.infrastructure.gateway.config.SessionCookieGenerator;
import com.salesflow.sales_service.infrastructure.gateway.dto.PersonDto;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonAdapter implements PersonPort {

    private final PersonGateway personGateway;
    private final SessionCookieGenerator cookieGenerator;

    public PersonAdapter(PersonGateway personGateway, SessionCookieGenerator cookieGenerator) {
        this.personGateway = personGateway;
        this.cookieGenerator = cookieGenerator;
    }

    @Override
    public Optional<PersonDto> getPersonById(String personId) {
        try {
            return Optional.of(
                    personGateway.getPerson(
                            personId,
                            "test",
                            cookieGenerator.generate()
                    )
            );
        } catch (FeignException.NotFound ex) {
            return Optional.empty();
        }
    }
}
