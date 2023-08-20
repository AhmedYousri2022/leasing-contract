package com.allane.leasing.stub.customer;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;

public class CustomerDetailsResponseDtoStub {

    public static CustomerDetailsResponseDto getDto() {

        CustomerDetailsResponseDto  responseDto = new CustomerDetailsResponseDto();
        responseDto.setId(UUID.fromString("eab78474-3329-42a1-b8b8-b13efd3c5572"));
        responseDto.setFirstName("Ahmed");
        responseDto.setLastName("Salama");
        responseDto.setBirthdate(LocalDate.of(1990, Month.MAY, 10));

        return responseDto;
    }
}
