package com.allane.leasing.stub.leasingcontract;

import com.allane.leasing.dto.leasingcontract.LeasingContractRequestDto;

public class LeasingContractRequestDtoStub {

    public static LeasingContractRequestDto getDto() {
        LeasingContractRequestDto responseDto = new LeasingContractRequestDto();
        responseDto.setContractNumber(151);
        responseDto.setMonthlyRate(1.5);
        responseDto.setCustomerId("4aa6538e-56d3-4a97-a98e-5967bb5bd843");
        responseDto.setVehicleId("ce20aa98-f9cb-4641-8fd8-11cb37484535");

        return responseDto;
    }

}
