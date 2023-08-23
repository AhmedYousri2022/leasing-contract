package com.allane.leasing.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractOverviewDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractOverviewResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.service.LeasingContractService;
import com.allane.leasing.validation.ValidUUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contracts")
public class LeasingContractController {

    private final LeasingContractService service;

    @GetMapping
    public List<LeasingContractOverviewResponseDto> getLeasingContractsOverview() {
        return service.getLeasingContractsOverview();
    }

    @GetMapping(path = "/details/{leasingContractId}")
    public LeasingContractOverviewDetailsResponseDto getLeasingContractOverviewDetails(@NotNull @ValidUUID @PathVariable UUID leasingContractId) {
        return service.getLeasingContractsOverviewDetails(leasingContractId);
    }

    @GetMapping(path = "/{leasingContractId}")
    public LeasingContractDetailsResponseDto getLeasingContract(@NotNull @ValidUUID @PathVariable UUID leasingContractId) {
        return service.getLeasingContractDetails(leasingContractId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeasingContractDetailsResponseDto createLeasingContract(@Valid @RequestBody LeasingContractRequestDto dto) {
        return service.createLeasingContract(dto);
    }

    @PutMapping(path = "/{leasingContractId}")
    public LeasingContractDetailsResponseDto updateLeasingContract(@NotNull @ValidUUID @PathVariable UUID leasingContractId,
                                                                   @Valid @RequestBody LeasingContractRequestDto dto) {
        return service.updateLeasingContract(leasingContractId, dto);
    }

    @GetMapping(path = "/{leasingContractId}/customer")
    public CustomerDetailsResponseDto getCustomerDetails(@ValidUUID @PathVariable UUID leasingContractId) {
        return service.getAssignCustomerDetails(leasingContractId);
    }

    @GetMapping(path = "/{leasingContractId}/vehicle")
    public VehicleDetailsResponseDto getVehicleDetails(@ValidUUID @PathVariable UUID leasingContractId) {
        return service.getAssignedVehicleDetails(leasingContractId);
    }
}
