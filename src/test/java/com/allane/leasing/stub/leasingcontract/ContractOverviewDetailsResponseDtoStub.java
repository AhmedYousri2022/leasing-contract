package com.allane.leasing.stub.leasingcontract;

import com.allane.leasing.dto.leasingcontract.ContractOverviewDetailsResponseDto;
import com.allane.leasing.stub.customer.CustomerDetailsResponseDtoStub;
import com.allane.leasing.stub.vehicle.VehicleDetailsResponseDtoStub;

public class ContractOverviewDetailsResponseDtoStub {

    public static ContractOverviewDetailsResponseDto getDto() {
        ContractOverviewDetailsResponseDto responseDto = new ContractOverviewDetailsResponseDto();
        responseDto.setContractNumber(151);
        responseDto.setMonthlyRate(1.5);
        responseDto.setCustomer(CustomerDetailsResponseDtoStub.getDto());
        responseDto.setVehicle(VehicleDetailsResponseDtoStub.getDto());

        return responseDto;
    }

}
