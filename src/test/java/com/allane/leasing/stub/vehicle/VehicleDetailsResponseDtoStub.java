package com.allane.leasing.stub.vehicle;

import java.util.UUID;

import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;

public class VehicleDetailsResponseDtoStub {

    public static VehicleDetailsResponseDto getDto() {

        VehicleDetailsResponseDto  responseDto = new VehicleDetailsResponseDto();
        responseDto.setId(UUID.fromString("eab78474-3329-42a1-b8b8-b13efd3c5572"));
        responseDto.setVehicleIdentificationNumber("132-123");
        responseDto.setPrice(15000D);
        responseDto.setBrand("AUDI");
        responseDto.setModel("A4");
        responseDto.setModelYear("2023");

        return responseDto;
    }
}
