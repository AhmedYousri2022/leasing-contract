package com.allane.leasing.stub.leasingcontract;

import java.util.UUID;

import com.allane.leasing.dto.leasingcontract.LeasingContractDetailsResponseDto;

public class LeasingContractDetailsResponseDtoStub {

    public static LeasingContractDetailsResponseDto getDto() {
        LeasingContractDetailsResponseDto responseDto = new LeasingContractDetailsResponseDto();
        responseDto.setId(UUID.fromString("10246594-ab01-4fc4-a34e-deb2804f967d"));
        responseDto.setContractNumber(151);
        responseDto.setMonthlyRate(1.5);
        responseDto.setCustomerSummary("mohamed salama");
        responseDto.setVehicleSummary("AUDI A4 (2023)");

        return responseDto;
    }

}
