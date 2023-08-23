package com.allane.leasing.controller;

import java.util.List;

import javax.validation.Valid;

import com.allane.leasing.dto.vehicle.VehicleDetailsRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.service.VehicleService;
import com.allane.leasing.validation.ValidUUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDetailsResponseDto addVehicle(@Valid @RequestBody VehicleDetailsRequestDto dto) {
        return service.addVehicleDetails(dto);
    }

    @GetMapping(path = "/{vehicleId}")
    public VehicleDetailsResponseDto getVehicle(@ValidUUID @PathVariable String vehicleId) {
        return service.getVehicleDetails(vehicleId);
    }

    @GetMapping
    public List<VehicleDetailsResponseDto> getVehicles(@RequestParam boolean assigned) {
        return service.getAssignedVehicles(assigned);
    }

    @PutMapping(path = "/{vehicleId}")
    public VehicleDetailsResponseDto updateVehicle(@ValidUUID @PathVariable String vehicleId,
                                                   @Valid @RequestBody VehicleDetailsRequestDto dto) {
        return service.updateVehicleDetails(vehicleId, dto);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{vehicleId}")
    public void deleteVehicle(@ValidUUID @PathVariable String vehicleId) {
        service.deleteVehicleDetails(vehicleId);
    }
}
