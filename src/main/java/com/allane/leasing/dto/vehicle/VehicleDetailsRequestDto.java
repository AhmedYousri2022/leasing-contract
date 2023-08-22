package com.allane.leasing.dto.vehicle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class VehicleDetailsRequestDto {

    @NotNull
    @Size(min = 1, max = 20, message = "should be between [1,20] characters")
    private String brand;

    @NotNull
    @Size(min = 1, max = 20, message = "should be between [1,20] characters")
    private String model;

    @NotNull
    @Size(min = 4, max = 4, message = "4 characters")
    private String modelYear;

    private String vehicleIdentificationNumber;

    @Range(min = 1, max = 1300000, message= "max price should be less than 1300000")
    private Double price;
}
