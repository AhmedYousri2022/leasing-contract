package com.allane.leasing.service;

import java.util.UUID;

import com.allane.leasing.DatabaseContainer;
import com.allane.leasing.dto.vehicle.VehicleDetailsRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.repository.VehicleRepository;
import com.allane.leasing.stub.vehicle.VehicleDetailsRequestDtoStub;
import com.allane.leasing.stub.vehicle.VehicleModelStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(initializers = {DatabaseContainer.class})
class VehicleServiceIT {

    @Autowired
    private VehicleRepository repository;

    @Autowired
    private VehicleService service;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void shouldGetVehicleDetails() {
        Vehicle vehicle = repository.save(VehicleModelStub.getDto());

        VehicleDetailsResponseDto vehicleDetails = service.getVehicleDetails(vehicle.getId());

        assertThat(vehicleDetails.getModel(), is(vehicle.getModel()));
        assertThat(vehicleDetails.getBrand(), is(vehicle.getBrand()));
        assertThat(vehicleDetails.getVehicleIdentificationNumber(), is(vehicle.getVehicleIdentificationNumber()));
        assertThat(vehicleDetails.getModelYear(), is(vehicle.getModelYear()));
        assertThat(vehicleDetails.getPrice(), is(vehicle.getPrice()));
    }

    @Test
    void shouldAddVehicleDetails() {
        VehicleDetailsRequestDto requestDto = VehicleDetailsRequestDtoStub.getDto();

        VehicleDetailsResponseDto responseDto = service.addVehicleDetails(requestDto);

        assertThat(responseDto.getModel(), is(requestDto.getModel()));
        assertThat(responseDto.getBrand(), is(requestDto.getBrand()));
        assertThat(responseDto.getVehicleIdentificationNumber(), is(requestDto.getVehicleIdentificationNumber()));
        assertThat(responseDto.getModelYear(), is(requestDto.getModelYear()));
        assertThat(responseDto.getPrice(), is(requestDto.getPrice()));
    }

    @Test
    void shouldDeleteVehicleDetails() {
        Vehicle customer = repository.save(VehicleModelStub.getDto());

        service.deleteVehicleDetails(customer.getId());

        assertThat(repository.findAll(), hasSize(0));
    }

    @Test
    void shouldUpdateVehicleDetails() {
        Vehicle vehicle = repository.save(VehicleModelStub.getDto());
        VehicleDetailsRequestDto dto = VehicleDetailsRequestDtoStub.getDto();
        dto.setModel("X6");

        VehicleDetailsResponseDto responseDto = service.updateVehicleDetails(vehicle.getId(), dto);

        assertThat(responseDto.getModel(), is(dto.getModel()));
        assertThat(responseDto.getBrand(), is(dto.getBrand()));
        assertThat(responseDto.getVehicleIdentificationNumber(), is(dto.getVehicleIdentificationNumber()));
        assertThat(responseDto.getModelYear(), is(dto.getModelYear()));
        assertThat(responseDto.getPrice(), is(dto.getPrice()));
    }

    @Test
    void shouldThrowCustomerNotfound() {
        Exception exception = assertThrows(
                NotFoundException.class,
                () -> service.updateVehicleDetails(UUID.fromString("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4"),
                                                   VehicleDetailsRequestDtoStub.getDto()),
                "Vehicle not found");

        assertThat(exception.getMessage(), is("Vehicle not found"));
    }
}
