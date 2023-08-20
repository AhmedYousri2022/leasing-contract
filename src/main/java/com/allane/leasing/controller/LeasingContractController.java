package com.allane.leasing.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.allane.leasing.dto.ContractOverviewResponseDto;
import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.ContractOverviewDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractRequestDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractUpdateRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.service.LeasingContractService;
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
//@Api(value = "leasing service", tags = {"contracts"})
public class LeasingContractController {

    private final LeasingContractService service;

    @GetMapping
    public List<ContractOverviewResponseDto> getLeasingContractsOverview() {
        return service.getLeasingContractsOverview();
    }

    @GetMapping(path = "/details/{leasingContractId}")
    public ContractOverviewDetailsResponseDto getLeasingContractsOverviewDetails(@NotNull @PathVariable UUID leasingContractId) {
        return service.getLeasingContractsOverviewDetails(leasingContractId);
    }

    @GetMapping(path = "/{contractNumber}")
    public LeasingContractDetailsResponseDto getLeasingContract(@NotNull @PathVariable int contractNumber) {
        return service.getLeasingContractDetails(contractNumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeasingContractDetailsResponseDto createLeasingContract(@Valid @RequestBody LeasingContractRequestDto dto) {
        return service.createLeasingContract(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/assign/{contractNumber}/vehicle/{vehicleId}")
    public void assignVehicle(@PathVariable int contractNumber, @PathVariable UUID vehicleId) {
        service.assignVehicle(contractNumber, vehicleId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/unassign/{contractNumber}/vehicle/{vehicleId}")
    public void unAssignVehicle(@PathVariable int contractNumber, @PathVariable UUID vehicleId) {
        service.unAssignVehicle(contractNumber, vehicleId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/assign/{contractNumber}/customer/{customerId}")
    public void assignCustomer(@PathVariable int contractNumber, @PathVariable UUID customerId) {
        service.assignCustomer(contractNumber, customerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/unassign/{contractNumber}/customer/{customerId}")
    public void unAssignCustomer(@PathVariable int contractNumber, @PathVariable UUID customerId) {
        service.unAssignCustomer(contractNumber, customerId);
    }

    @PutMapping(path = "/{leasingContractId}")
    public LeasingContractDetailsResponseDto updateLeasingContract(@NotNull @PathVariable UUID leasingContractId,
                                                                   @Valid @RequestBody LeasingContractUpdateRequestDto dto) {
        return service.updateLeasingContract(leasingContractId, dto);
    }

        @GetMapping(path = "/{leasingContractId}/customer/{customerId}")
        public CustomerDetailsResponseDto getCustomerDetails(@PathVariable UUID leasingContractId, @PathVariable UUID customerId) {
             return service.getContractCustomerDetails(leasingContractId, customerId);
        }

        @GetMapping(path = "/{leasingContractId}/vehicle/{vehicleId}")
        public VehicleDetailsResponseDto getVehicleDetails(@PathVariable UUID leasingContractId, @PathVariable UUID vehicleId) {
            return service.getContractVehicleDetails(leasingContractId, vehicleId);
        }
}
