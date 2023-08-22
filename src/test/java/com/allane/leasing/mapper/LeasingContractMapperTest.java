package com.allane.leasing.mapper;

import java.math.BigDecimal;

import com.allane.leasing.dto.leasingcontract.LeasingContractOverviewDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractOverviewResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractRequestDto;
import com.allane.leasing.model.LeasingContract;
import com.allane.leasing.stub.leasingcontract.LeasingContractModelDtoStub;
import com.allane.leasing.stub.leasingcontract.LeasingContractRequestDtoStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class LeasingContractMapperTest {

    private final LeasingContractMapper leasingContractMapper = Mappers.getMapper(LeasingContractMapper.class);

    @Test
    void shouldMapToLeasingContract() {
        LeasingContractRequestDto dto = LeasingContractRequestDtoStub.getDto();
        LeasingContract leasingContract = leasingContractMapper.toLeasingContract(dto);

        assertThat(leasingContract.getContractNumber(), is(dto.getContractNumber()));
        assertThat(leasingContract.getMonthlyRate(), is(BigDecimal.valueOf(dto.getMonthlyRate())));
    }

    @Test
    void shouldMapToLeasingContractOverviewResponseDto() {
        LeasingContract contract = LeasingContractModelDtoStub.getModel();

        LeasingContractOverviewResponseDto dto = leasingContractMapper.toDto(contract);

        assertThat(dto.getContractNumber(), is(contract.getContractNumber()));
        assertThat(dto.getMonthlyRate(), is(contract.getMonthlyRate().doubleValue()));
        assertThat(dto.getCustomerSummary(), is(contract.getCustomer().getCustomerSummary()));
        assertThat(dto.getVehicleSummary(), is(contract.getVehicle().getVehicleSummary()));
        assertThat(dto.getVehicleIdentificationNumber(), is(contract.getVehicle().getVehicleIdentificationNumber()));
        assertThat(dto.getVehiclePrice(), is(contract.getVehicle().getPrice()));
    }

    @Test
    void shouldMapContractOverviewDto() {
        LeasingContract contract = LeasingContractModelDtoStub.getModel();

        LeasingContractOverviewDetailsResponseDto contractOverviewDetailsDto = leasingContractMapper.toContractOverviewDetailsDto(
                contract);

        assertThat(contractOverviewDetailsDto.getContractNumber(), is(contract.getContractNumber()));
        assertThat(contractOverviewDetailsDto.getMonthlyRate(), is(contract.getMonthlyRate().doubleValue()));
        assertThat(contractOverviewDetailsDto.getCustomer().getFirstName(), is(contract.getCustomer().getFirstName()));
        assertThat(contractOverviewDetailsDto.getCustomer().getLastName(), is(contract.getCustomer().getLastName()));
        assertThat(contractOverviewDetailsDto.getCustomer().getBirthdate(), is(contract.getCustomer().getBirthdate()));
        assertThat(contractOverviewDetailsDto.getVehicle().getModel(), is(contract.getVehicle().getModel()));
        assertThat(contractOverviewDetailsDto.getVehicle().getBrand(), is(contract.getVehicle().getBrand()));
        assertThat(contractOverviewDetailsDto.getVehicle().getModelYear(), is(contract.getVehicle().getModelYear()));
        assertThat(contractOverviewDetailsDto.getVehicle().getPrice(), is(contract.getVehicle().getPrice()));
        assertThat(contractOverviewDetailsDto.getVehicle().getVehicleIdentificationNumber(),
                   is(contract.getVehicle().getVehicleIdentificationNumber()));
    }
}
