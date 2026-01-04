package com.salesflow.sales_service.mock;

import com.salesflow.sales_service.domain.enums.PersonStatus;
import com.salesflow.sales_service.infrastructure.gateway.dto.PersonDto;

public class PersonDtoMock {

    public static PersonDto active() {
        return new PersonDto(
                "12345678900",
                "João Silva",
                "joao@email.com",
                PersonStatus.ACTIVE.name(),
                "21999999999"
        );
    }

    public static PersonDto inactive() {
        return new PersonDto(
                "12345678900",
                "João Silva",
                "joao@email.com",
                PersonStatus.INACTIVE.name(),
                "21999999999"
        );
    }
}
