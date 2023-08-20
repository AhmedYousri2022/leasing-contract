package com.allane.leasing.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class VehicleDetailsRequestDto {

    private String brand;

    private String model;

    private String modelYear;

    private String vehicleIdentificationNumber;

    private Double price;
}
