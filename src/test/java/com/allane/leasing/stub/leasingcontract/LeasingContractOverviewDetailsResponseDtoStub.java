package com.allane.leasing.stub.leasingcontract;

import com.allane.leasing.dto.leasingcontract.LeasingContractOverviewDetailsResponseDto;
import com.allane.leasing.stub.customer.CustomerDetailsResponseDtoStub;
import com.allane.leasing.stub.vehicle.VehicleDetailsResponseDtoStub;

public class LeasingContractOverviewDetailsResponseDtoStub {

    public static LeasingContractOverviewDetailsResponseDto getDto() {
        LeasingContractOverviewDetailsResponseDto responseDto = new LeasingContractOverviewDetailsResponseDto();
        responseDto.setContractNumber(151);
        responseDto.setMonthlyRate(1.5);
        responseDto.setCustomer(CustomerDetailsResponseDtoStub.getDto());
        responseDto.setVehicle(VehicleDetailsResponseDtoStub.getDto());

        return responseDto;
    }

}
