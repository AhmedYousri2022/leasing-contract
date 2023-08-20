package com.allane.leasing.stub.customer;

import java.time.LocalDate;
import java.time.Month;

import com.allane.leasing.dto.customer.CustomerDetailsRequestDto;

public class CustomerDetailsRequestDtoStub {

    public static CustomerDetailsRequestDto getDto() {
        return CustomerDetailsRequestDto.builder()
                .firstName("Ahmed")
                .lastName("Salama")
                .birthdate(LocalDate.of(1990, Month.MAY, 10))
                .build();
    }
}
