package com.allane.leasing.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.ContractOverviewDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.ContractOverviewResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractRequestDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractUpdateRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.exception.AssociatedException;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.exception.VehicleAssignedException;
import com.allane.leasing.mapper.LeasingContractMapper;
import com.allane.leasing.model.Customer;
import com.allane.leasing.model.LeasingContract;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.repository.LeasingContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeasingContractService {

    private final LeasingContractRepository leasingContractRepository;

    private final CustomerService customerService;

    private final VehicleService vehicleService;

    private final LeasingContractMapper leasingContractMapper = Mappers.getMapper(LeasingContractMapper.class);

    @Transactional(readOnly = true)
    public List<ContractOverviewResponseDto> getLeasingContractsOverview() {
        List<LeasingContract> leasingContractOverview = leasingContractRepository.findAll();
        return leasingContractOverview.stream().map(leasingContractMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContractOverviewDetailsResponseDto getLeasingContractsOverviewDetails(UUID leasingContractId) {
        LeasingContract leasingContractOverview = getLeasingContract(leasingContractId);
        return leasingContractMapper.toContractOverviewDetailsDto(leasingContractOverview);
    }

    @Transactional(readOnly = true)
    public LeasingContractDetailsResponseDto getLeasingContractDetails(UUID leasingContractId) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);
        return leasingContractMapper.toContractDetailsDto(leasingContract);
    }

    @Transactional
    public LeasingContractDetailsResponseDto createLeasingContract(LeasingContractRequestDto dto) {
        Customer customer = customerService.getCustomer(dto.getCustomerId());
        Vehicle vehicle = vehicleService.getVehicle(dto.getVehicleId());
        isAssignedVehicle(vehicle);
        vehicle.setAssigned(true);

        LeasingContract contract = leasingContractMapper.toLeasingContract(dto);
        contract.setVehicle(vehicle);
        contract.setCustomer(customer);

        LeasingContract leasingContract = leasingContractRepository.save(contract);

        return leasingContractMapper.toContractDetailsDto(leasingContract);
    }

    @Transactional
    public void unAssignVehicle(UUID leasingContractId, UUID vehicleId) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);

        if (isVehicleAssociatedToTheContract(leasingContract, vehicleId)) {
            vehicle.setAssigned(false);
            leasingContract.setVehicle(null);
        } else {
            throw new AssociatedException("The vehicle is not associated the contract");
        }
    }

    @Transactional
    public void assignVehicle(UUID leasingContractId, UUID vehicleId) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        isAssignedVehicle(vehicle);
        if (isVehicleAssociatedToTheContract(leasingContract, vehicleId)) {
            throw new AssociatedException("the vehicle already assigned to the contract");
        }
        vehicle.setAssigned(true);
        leasingContract.setVehicle(vehicle);
    }

    @Transactional
    public void unAssignCustomer(UUID leasingContractId, UUID customerId) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);
        customerService.getCustomer(customerId);

        if (!isCustomerAssignedToContract(leasingContract, customerId)) {
            log.error("Customer with id {} is not associated to provided contract with contract id {}",
                      customerId, leasingContractId);
            throw new AssociatedException("the customer is not associated to the contract");
        }

        leasingContract.setCustomer(null);
    }

    @Transactional
    public void assignCustomer(UUID leasingContractId, UUID customerId) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);

        if (isCustomerAssignedToContract(leasingContract, customerId)) {
            log.error("Customer with id {} is already associated to provided contract with contract id {}",
                      customerId, leasingContractId);
            throw new AssociatedException("the customer is associated to the contract");
        }
        Customer customer = customerService.getCustomer(customerId);
        leasingContract.setCustomer(customer);
    }

    @Transactional
    public LeasingContractDetailsResponseDto updateLeasingContract(UUID leasingContractId, LeasingContractUpdateRequestDto dto) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);

        leasingContractMapper.updateLeasingContractFromDto(dto, leasingContract);

        LeasingContract updated = leasingContractRepository.save(leasingContract);

        return leasingContractMapper.toContractDetailsDto(updated);
    }

    @Transactional(readOnly = true)
    public CustomerDetailsResponseDto getContractCustomerDetails(UUID leasingContractId, UUID customerId) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);
        customerService.getCustomer(customerId);

        if (!isCustomerAssignedToContract(leasingContract, customerId)) {
            log.error("Customer with id {} is not associated to provided contract with leasing Contract Id {}",
                      customerId, leasingContractId);

            throw new AssociatedException("the customer is not associated to the contract");
        }

        return customerService.getCustomerDetails(customerId);
    }

    @Transactional(readOnly = true)
    public VehicleDetailsResponseDto getContractVehicleDetails(UUID leasingContractId, UUID vehicleId) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);
        vehicleService.getVehicle(vehicleId);

        if (!isVehicleAssociatedToTheContract(leasingContract, vehicleId)) {
            log.error("Vehicle with id {} is not associated to provided contract with leasing Contract Id {}",
                      vehicleId, leasingContractId);

            throw new AssociatedException("the vehicle is not associated to the contract");
        }

        return vehicleService.getVehicleDetails(vehicleId);
    }

    private boolean isVehicleAssociatedToTheContract(LeasingContract leasingContract, UUID vehicleId) {
        Optional<LeasingContract> contract = leasingContractRepository.findLeasingContractByIdAndVehicleId(
                leasingContract.getId(), vehicleId);

        return contract.isPresent();
    }

    private boolean isCustomerAssignedToContract(LeasingContract leasingContract, UUID customerId) {
        Optional<LeasingContract> contract = leasingContractRepository.findLeasingContractByIdAndCustomerId(
                leasingContract.getId(), customerId);
        return contract.isPresent();
    }

    private void isAssignedVehicle(Vehicle vehicle) {
        if (vehicle.isAssigned()) {
            log.error("Vehicle id {} already assigned to a contract", vehicle.getId());
            throw new VehicleAssignedException("Vehicle already assigned to a contract");
        }
    }

    private LeasingContract getLeasingContract(UUID leasingContractId) {
        return leasingContractRepository.findById(leasingContractId)
                .orElseThrow(() -> new NotFoundException("Contract not found"));
    }
}
