package com.salesflow.sales_service.infrastructure.gateway.adapter;

import com.salesflow.sales_service.infrastructure.gateway.PersonGateway;
import com.salesflow.sales_service.infrastructure.gateway.config.SessionCookieGenerator;
import com.salesflow.sales_service.infrastructure.gateway.dto.PersonDto;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonAdapterTest {
    @Mock
    private PersonGateway personGateway;

    @Mock
    private SessionCookieGenerator cookieGenerator;

    @InjectMocks
    private PersonAdapter adapter;


    @Test
    void shouldReturnPersonWhenFound() {

        var personDto = new PersonDto(
                "1",
                "João Silva",
                "12345678900",
                "ACTIVE",
                "1990-01-01"
        );

        when(cookieGenerator.generate())
                .thenReturn("JSESSIONID=abc");

        when(personGateway.getPerson(
                eq("12345678900"),
                eq("test"),
                eq("JSESSIONID=abc")
        )).thenReturn(personDto);

        Optional<PersonDto> result =
                adapter.getPersonById("12345678900");

        assertTrue(result.isPresent());
        assertEquals("ACTIVE", result.get().status());

        verify(cookieGenerator).generate();
        verify(personGateway).getPerson(
                any(),
                any(),
                any()
        );
    }

    @Test
    void shouldReturnEmptyWhenPersonNotFound() {

        when(cookieGenerator.generate())
                .thenReturn("JSESSIONID=abc");

        FeignException notFound =
                mock(FeignException.NotFound.class);

        when(personGateway.getPerson(
                any(),
                any(),
                any()
        )).thenThrow(notFound);

        Optional<PersonDto> result =
                adapter.getPersonById("12345678900");

        assertTrue(result.isEmpty());

        verify(cookieGenerator).generate();
        verify(personGateway).getPerson(any(), any(), any());
    }


    @Test
    void shouldThrowExceptionWhenFeignErrorOccurs() {

        when(cookieGenerator.generate())
                .thenReturn("JSESSIONID=abc");

        FeignException feignException =
                mock(FeignException.class);

        when(feignException.status()).thenReturn(500);

        when(personGateway.getPerson(
                any(),
                any(),
                any()
        )).thenThrow(feignException);

        assertThrows(
                FeignException.class,
                () -> adapter.getPersonById("12345678900")
        );

        verify(cookieGenerator).generate();
        verify(personGateway).getPerson(any(), any(), any());
    }

}