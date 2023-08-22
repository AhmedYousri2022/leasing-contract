package com.allane.leasing.mapper;

import com.allane.leasing.dto.vehicle.VehicleDetailsRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.stub.vehicle.VehicleDetailsRequestDtoStub;
import com.allane.leasing.stub.vehicle.VehicleModelStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VehicleMapperTest {

    private final VehicleMapper vehicleMapper = Mappers.getMapper(VehicleMapper.class);

    @Test
    void toResponseDto() {
        Vehicle vehicle = VehicleModelStub.getModel();

        VehicleDetailsResponseDto responseDto = vehicleMapper.toResponseDto(vehicle);

        assertThat(responseDto.getBrand(), is(vehicle.getBrand()));
        assertThat(responseDto.getModel(), is(vehicle.getModel()));
        assertThat(responseDto.getModelYear(), is(vehicle.getModelYear()));
        assertThat(responseDto.getVehicleIdentificationNumber(), is(vehicle.getVehicleIdentificationNumber()));
        assertThat(responseDto.getPrice(), is(vehicle.getPrice()));
    }

    @Test
    void toVehicle() {
        VehicleDetailsRequestDto dto = VehicleDetailsRequestDtoStub.getDto();

        Vehicle vehicle = vehicleMapper.toVehicle(dto);

        assertThat(vehicle.getBrand(), is(dto.getBrand()));
        assertThat(vehicle.getModel(), is(dto.getModel()));
        assertThat(vehicle.getModelYear(), is(dto.getModelYear()));
        assertThat(vehicle.getVehicleIdentificationNumber(), is(dto.getVehicleIdentificationNumber()));
        assertThat(vehicle.getPrice(), is(dto.getPrice()));
    }
}
