package com.salesflow.sales_service.infrastructure.gateway.adapter;

import com.salesflow.sales_service.application.port.out.PersonPort;
import com.salesflow.sales_service.infrastructure.gateway.PersonGateway;
import com.salesflow.sales_service.infrastructure.gateway.config.SessionCookieGenerator;
import com.salesflow.sales_service.infrastructure.gateway.dto.PersonDto;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonAdapter implements PersonPort {

    private static final Logger log = LoggerFactory.getLogger(PersonAdapter.class);

    private final PersonGateway personGateway;
    private final SessionCookieGenerator cookieGenerator;

    public PersonAdapter(PersonGateway personGateway, SessionCookieGenerator cookieGenerator) {
        this.personGateway = personGateway;
        this.cookieGenerator = cookieGenerator;
    }

    @Override
    public Optional<PersonDto> getPersonById(String personId) {

        String sessionCookie = cookieGenerator.generate();

        log.info(
                "[GATEWAY][PERSON] Fetching person | personId={}",
                personId
        );

        try {
            PersonDto person = personGateway.getPerson(
                    personId,
                    "test",
                    sessionCookie
            );

            log.info(
                    "[GATEWAY][PERSON] Person found | personId={} | status={}",
                    personId,
                    person.status()
            );

            return Optional.of(person);

        } catch (FeignException.NotFound ex) {

            log.warn(
                    "[GATEWAY][PERSON] Person not found | personId={}",
                    personId
            );
            return Optional.empty();

        } catch (FeignException ex) {

            log.error(
                    "[GATEWAY][PERSON] Error calling Person service | personId={} | status={} | message={}",
                    personId,
                    ex.status(),
                    ex.getMessage()
            );
            throw ex;
        }
    }
}
