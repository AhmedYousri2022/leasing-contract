package com.allane.leasing.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.allane.leasing.dto.vehicle.VehicleDetailsRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.mapper.VehicleMapper;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper = Mappers.getMapper(VehicleMapper.class);

    @Transactional
    public VehicleDetailsResponseDto addVehicleDetails(VehicleDetailsRequestDto dto) {
        Vehicle vehicle = vehicleRepository.save(vehicleMapper.toVehicle(dto));
        return vehicleMapper.toResponseDto(vehicle);
    }

    @Transactional(readOnly = true)
    public VehicleDetailsResponseDto getVehicleDetails(String vehicleId) {
        Vehicle vehicle = getVehicle(vehicleId);
        return vehicleMapper.toResponseDto(vehicle);
    }

    @Transactional
    public void deleteVehicleDetails(String vehicleId) {
        Vehicle vehicle = getVehicle(vehicleId);
        vehicleRepository.delete(vehicle);
    }

    @Transactional
    public VehicleDetailsResponseDto updateVehicleDetails(String vehicleId, VehicleDetailsRequestDto vehicleDetailsRequestDto) {
        Vehicle vehicle = getVehicle(vehicleId);
        vehicleMapper.updateVehicleFromDto(vehicleDetailsRequestDto, vehicle);
        Vehicle saved = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponseDto(saved);
    }

    public Vehicle getVehicle(String vehicleId) {
        return vehicleRepository.findById(UUID.fromString(vehicleId))
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));
    }

    @Transactional(readOnly = true)
    public List<VehicleDetailsResponseDto> getAssignedVehicles(boolean assigned) {
        List<Vehicle> vehicles = vehicleRepository.findByAssigned(assigned);
        return vehicles.stream().map(vehicleMapper::toResponseDto).collect(Collectors.toList());
    }
}
