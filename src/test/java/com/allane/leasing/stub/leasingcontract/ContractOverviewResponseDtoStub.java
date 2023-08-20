package com.allane.leasing.stub.leasingcontract;

import com.allane.leasing.dto.leasingcontract.ContractOverviewResponseDto;

public class ContractOverviewResponseDtoStub {

    public static ContractOverviewResponseDto getDto() {
        ContractOverviewResponseDto responseDto = new ContractOverviewResponseDto();
        responseDto.setContractNumber(151);
        responseDto.setVehiclePrice(15000d);
        responseDto.setMonthlyRate(1.5);
        responseDto.setCustomerSummary("mohamed salama");
        responseDto.setVehicleSummary("AUDI A4 (2023)");

        return responseDto;
    }

}
