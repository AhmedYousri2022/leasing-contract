package com.allane.leasing.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.allane.leasing.dto.vehicle.VehicleDetailsRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/vehicles")
//@Api(value = "leasing service", tags = {"contracts"})
public class VehicleController {

    private final VehicleService service;

    //    @ApiOperation(value = "Add new Person")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDetailsResponseDto addVehicle(@Valid @RequestBody VehicleDetailsRequestDto dto) {
        return service.addVehicleDetails(dto);
    }

    //    @ApiOperation(value = "get person details")
    @GetMapping(path = "/{vehicleId}")
    public VehicleDetailsResponseDto getVehicle(@NotNull @PathVariable UUID vehicleId) {
        return service.getVehicleDetails(vehicleId);
    }

    @GetMapping
    public List<VehicleDetailsResponseDto> getVehicle(@RequestParam boolean assigned) {
        return service.getAssignedVehicles(assigned);
    }

    //    @ApiOperation(value = "Change a name of a person")
    @PutMapping(path = "/{vehicleId}")
    public VehicleDetailsResponseDto updateVehicle(@NotNull @PathVariable UUID vehicleId,
                                                   @Valid @RequestBody VehicleDetailsRequestDto dto) {
        return service.updateVehicleDetails(vehicleId, dto);
    }

    //    @ApiOperation(value = "delete person")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{vehicleId}")
    public void deleteVehicle(@Valid @PathVariable UUID vehicleId) {
        service.deleteVehicleDetails(vehicleId);
    }
}
