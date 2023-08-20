package com.allane.leasing.dto.vehicle;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class VehicleDetailsResponseDto {

    private UUID id;

    private String brand;

    private String model;

    private String modelYear;

    private String vehicleIdentificationNumber;

    private Double price;
}
