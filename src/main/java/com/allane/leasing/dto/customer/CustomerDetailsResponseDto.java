package com.allane.leasing.dto.customer;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerDetailsResponseDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate birthdate;
}
