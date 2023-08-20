package com.allane.leasing.mapper;

import com.allane.leasing.dto.ContractOverviewResponseDto;
import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.ContractOverviewDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractRequestDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractUpdateRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.model.LeasingContract;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeasingContractMapper {

    LeasingContract toLeasingContract(LeasingContractRequestDto leasingContractRequestDto);

    LeasingContract toLeasingContract(LeasingContractUpdateRequestDto leasingContractUpdateRequestDto);

    @Mapping(target = "contractNumber", source = "leasingContract.contractNumber")
    @Mapping(target = "monthlyRate", source = "leasingContract.monthlyRate")
    ContractOverviewResponseDto toDto(LeasingContract leasingContract);

    @AfterMapping
    default void setContractOverviewDto(@MappingTarget ContractOverviewResponseDto dto, LeasingContract leasingContract) {
        if (leasingContract.getCustomer() != null) {
            dto.setCustomerSummary(leasingContract.getCustomer().getCustomerSummary());
        }

        if (leasingContract.getVehicle() != null) {
            dto.setVehicleSummary(leasingContract.getVehicle().getVehicleSummary());
            dto.setVehiclePrice(leasingContract.getVehicle().getPrice());
        }

        if (leasingContract.getVehicle() != null && leasingContract.getVehicle().getVehicleIdentificationNumber() == null) {
            dto.setVehicleIdentificationNumber("-");
        } else {
            dto.setVehicleIdentificationNumber(leasingContract.getVehicle().getVehicleIdentificationNumber());
        }
    }

    @Mapping(target = "contractNumber", source = "leasingContract.contractNumber")
    @Mapping(target = "monthlyRate", source = "leasingContract.monthlyRate")
    @Mapping(target = "vehicleIdentificationNumber", source = "leasingContract.vehicle.vehicleIdentificationNumber", qualifiedByName = "getVin")
    LeasingContractDetailsResponseDto toContractDetailsDto(LeasingContract leasingContract);

    @Named("getVin")
    default String getVin(String vin) {
        if (vin == null || vin.isEmpty()) {
            return "-";
        }
        return vin;
    }

    @Mapping(target = "contractNumber", source = "leasingContract.contractNumber")
    @Mapping(target = "monthlyRate", source = "leasingContract.monthlyRate")
    ContractOverviewDetailsResponseDto toContractOverviewDetailsDto(LeasingContract leasingContract);

    @AfterMapping
    default void setContractDetailsDto(@MappingTarget ContractOverviewDetailsResponseDto dto,
                                       LeasingContract leasingContract) {
        setCustomer(dto, leasingContract);

        setVehicle(dto, leasingContract);
    }

    private static void setCustomer(ContractOverviewDetailsResponseDto dto, LeasingContract leasingContract) {
        if (leasingContract.getCustomer() != null) {
            CustomerDetailsResponseDto customerDto = new CustomerDetailsResponseDto();
            if (leasingContract.getCustomer().getFirstName() != null) {
                customerDto.setFirstName(leasingContract.getCustomer().getFirstName());
            }
            if (leasingContract.getCustomer().getLastName() != null) {
                customerDto.setLastName(leasingContract.getCustomer().getLastName());
            }
            if (leasingContract.getCustomer().getBirthdate() != null) {
                customerDto.setBirthdate(leasingContract.getCustomer().getBirthdate());
            }
            dto.setCustomer(customerDto);
        }
    }

    private static void setVehicle(ContractOverviewDetailsResponseDto dto, LeasingContract leasingContract) {
        if (leasingContract.getVehicle() != null) {
            VehicleDetailsResponseDto vehicleDto = new VehicleDetailsResponseDto();
            if (leasingContract.getVehicle().getBrand() != null) {
                vehicleDto.setBrand(leasingContract.getVehicle().getBrand());
            }
            if (leasingContract.getVehicle().getPrice() != null) {
                vehicleDto.setPrice(leasingContract.getVehicle().getPrice());
            }
            if (leasingContract.getVehicle().getModel() != null) {
                vehicleDto.setModel(leasingContract.getVehicle().getModel());
            }
            if (leasingContract.getVehicle().getModelYear() != null) {
                vehicleDto.setModelYear(leasingContract.getVehicle().getModelYear());
            }
            if (leasingContract.getVehicle().getVehicleIdentificationNumber() != null) {
                vehicleDto.setVehicleIdentificationNumber(leasingContract.getVehicle().getVehicleIdentificationNumber());
            } else {
                vehicleDto.setVehicleIdentificationNumber("-");
            }
            dto.setVehicle(vehicleDto);
        }
    }

    @AfterMapping
    default void setContractDetailsDto(@MappingTarget LeasingContractDetailsResponseDto contractDetailsResponseDto,
                                       LeasingContract leasingContract) {
        if (leasingContract.getCustomer() != null) {
            contractDetailsResponseDto.setCustomerSummary(leasingContract.getCustomer().getCustomerSummary());
        }

        if (leasingContract.getVehicle() != null) {
            contractDetailsResponseDto.setVehicleSummary(leasingContract.getVehicle().getVehicleSummary());
        }
        if (leasingContract.getVehicle() != null && leasingContract.getVehicle().getVehicleIdentificationNumber() == null) {
            contractDetailsResponseDto.setVehicleIdentificationNumber("-");
        } else {
            contractDetailsResponseDto.setVehicleIdentificationNumber(
                    leasingContract.getVehicle().getVehicleIdentificationNumber());
        }
    }


    void updateLeasingContractFromDto(LeasingContractUpdateRequestDto dto, @MappingTarget LeasingContract leasingContract);
}
