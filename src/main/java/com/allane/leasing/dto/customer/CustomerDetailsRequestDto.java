package com.allane.leasing.dto.customer;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerDetailsRequestDto {

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;
}
