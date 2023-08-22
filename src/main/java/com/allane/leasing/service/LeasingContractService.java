package com.allane.leasing.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.ContractOverviewDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.ContractOverviewResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractRequestDto;
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
        checkAssignedVehicle(vehicle);
        vehicle.setAssigned(true);

        LeasingContract contract = leasingContractMapper.toLeasingContract(dto);
        contract.setVehicle(vehicle);
        contract.setCustomer(customer);

        LeasingContract leasingContract = leasingContractRepository.save(contract);

        return leasingContractMapper.toContractDetailsDto(leasingContract);
    }


    @Transactional
    public LeasingContractDetailsResponseDto updateLeasingContract(UUID leasingContractId, LeasingContractRequestDto dto) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);
        setVehicle(dto, leasingContract);
        setCustomer(dto, leasingContract);
        leasingContract.setContractNumber(dto.getContractNumber());
        leasingContract.setMonthlyRate(BigDecimal.valueOf(dto.getMonthlyRate()));
        leasingContractMapper.updateLeasingContractFromDto(dto, leasingContract);
        LeasingContract updated = leasingContractRepository.save(leasingContract);
        return leasingContractMapper.toContractDetailsDto(updated);
    }

    private void setCustomer(LeasingContractRequestDto dto, LeasingContract leasingContract) {
        if (leasingContract.getCustomer() != null && dto.getCustomerId() == null) {
            leasingContract.setCustomer(null);
        } else {
            Customer updatedCustomer = customerService.getCustomer(dto.getCustomerId());
            leasingContract.setCustomer(updatedCustomer);
        }
    }

    private void setVehicle(LeasingContractRequestDto dto, LeasingContract leasingContract) {
        if (leasingContract.getVehicle() != null && dto.getVehicleId() == null) {
            leasingContract.getVehicle().setAssigned(false);
            leasingContract.setVehicle(null);
        } else if (leasingContract.getVehicle() == null && dto.getVehicleId() != null) {
            //assign
            assignNewVehicleToContract(dto, leasingContract);
        } else if (leasingContract.getVehicle() != null && !leasingContract.getVehicle().getId().equals(dto.getVehicleId())) {
            //update
            assignNewVehicleToContract(dto, leasingContract);
            leasingContract.getVehicle().setAssigned(false);
        }
    }

    private void assignNewVehicleToContract(LeasingContractRequestDto dto, LeasingContract leasingContract) {
        Vehicle updatedVehicle = vehicleService.getVehicle(dto.getVehicleId());
        checkAssignedVehicle(updatedVehicle);
        updatedVehicle.setAssigned(true);
        leasingContract.setVehicle(updatedVehicle);
    }


    @Transactional(readOnly = true)
    public CustomerDetailsResponseDto getAssignCustomerDetails(UUID leasingContractId) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);

        if (leasingContract.getCustomer() == null) {
            log.error("No customer is associated to provided contract with leasing Contract Id {}",
                      leasingContractId);
            throw new AssociatedException("No customer is associated to the contract");
        }
        return customerService.getCustomerDetails(leasingContract.getCustomer().getId());
    }

    @Transactional(readOnly = true)
    public VehicleDetailsResponseDto getAssignedVehicleDetails(UUID leasingContractId) {
        LeasingContract leasingContract = getLeasingContract(leasingContractId);
        if (leasingContract.getVehicle() == null) {
            log.error("No Vehicle is associated to provided contract with leasing Contract Id {}",
                      leasingContractId);
            throw new AssociatedException("No Vehicle is associated to the contract");
        }
        return vehicleService.getVehicleDetails(leasingContract.getVehicle().getId());
    }

    private void checkAssignedVehicle(Vehicle vehicle) {
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
