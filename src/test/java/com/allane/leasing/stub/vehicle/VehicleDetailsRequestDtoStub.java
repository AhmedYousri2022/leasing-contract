package com.allane.leasing.stub.vehicle;

import com.allane.leasing.dto.vehicle.VehicleDetailsRequestDto;

public class VehicleDetailsRequestDtoStub {

    public static VehicleDetailsRequestDto getDto() {

        VehicleDetailsRequestDto responseDto = new VehicleDetailsRequestDto();
        responseDto.setVehicleIdentificationNumber("132-123");
        responseDto.setPrice(15000D);
        responseDto.setBrand("AUDI");
        responseDto.setModel("A4");
        responseDto.setModelYear("2023");

        return responseDto;
    }
}
