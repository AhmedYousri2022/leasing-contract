package com.allane.leasing.dto.customer;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull
    @Size(min = 3, max = 20, message = " firstName should be between [3,20] characters")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20, message = " lastName should be between [3,20] characters")
    private String lastName;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;
}
