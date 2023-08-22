package com.allane.leasing.service;

import java.util.Optional;
import java.util.UUID;

import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.repository.VehicleRepository;
import com.allane.leasing.stub.customer.CustomerModelStub;
import com.allane.leasing.stub.vehicle.VehicleDetailsRequestDtoStub;
import com.allane.leasing.stub.vehicle.VehicleModelStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository repository;

    @InjectMocks
    private VehicleService service;

    @Test
    void shouldGetVehicleDetails() {
        Vehicle vehicle = VehicleModelStub.getModel();
        when(repository.findById(any())).thenReturn(Optional.of(vehicle));

        VehicleDetailsResponseDto vehicleDetails = service.getVehicleDetails(CustomerModelStub.getModel().getId());

        assertThat(vehicleDetails.getBrand(), is(vehicle.getBrand()));
        assertThat(vehicleDetails.getModel(), is(vehicle.getModel()));
        assertThat(vehicleDetails.getVehicleIdentificationNumber(), is(vehicle.getVehicleIdentificationNumber()));
        assertThat(vehicleDetails.getModelYear(), is(vehicle.getModelYear()));
        assertThat(vehicleDetails.getPrice(), is(vehicle.getPrice()));
    }

    @Test
    void shouldAddVehicleDetails() {
        Vehicle vehicle = VehicleModelStub.getModel();
        when(repository.save(any())).thenReturn(vehicle);

        VehicleDetailsResponseDto vehicleDetailsResponseDto = service.addVehicleDetails(
                VehicleDetailsRequestDtoStub.getDto());

        assertThat(vehicleDetailsResponseDto.getBrand(), is(vehicle.getBrand()));
        assertThat(vehicleDetailsResponseDto.getModel(), is(vehicle.getModel()));
        assertThat(vehicleDetailsResponseDto.getVehicleIdentificationNumber(), is(vehicle.getVehicleIdentificationNumber()));
        assertThat(vehicleDetailsResponseDto.getModelYear(), is(vehicle.getModelYear()));
        assertThat(vehicleDetailsResponseDto.getPrice(), is(vehicle.getPrice()));
    }

    @Test
    void shouldUpdateVehicleDetails() {
        Vehicle vehicle = VehicleModelStub.getModel();
        Vehicle updated = VehicleModelStub.getModel();
        updated.setModel("AUDI");

        when(repository.findById(any())).thenReturn(Optional.of(vehicle));
        when(repository.save(any())).thenReturn(updated);

        VehicleDetailsResponseDto vehicleDetailsResponseDto = service.updateVehicleDetails(vehicle.getId(),
                                                                                           VehicleDetailsRequestDtoStub.getDto());

        assertThat(vehicleDetailsResponseDto.getBrand(), is(updated.getBrand()));
        assertThat(vehicleDetailsResponseDto.getModel(), is(updated.getModel()));
        assertThat(vehicleDetailsResponseDto.getVehicleIdentificationNumber(), is(updated.getVehicleIdentificationNumber()));
        assertThat(vehicleDetailsResponseDto.getModelYear(), is(updated.getModelYear()));
        assertThat(vehicleDetailsResponseDto.getPrice(), is(updated.getPrice()));
    }

    @Test
    void shouldThrowVehicleNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                                                   () -> service.deleteVehicleDetails(
                                                           UUID.fromString("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4")));

        assertThat(exception.getMessage(), is("Vehicle not found"));
    }
}
