package com.allane.leasing.stub.leasingcontract;

import com.allane.leasing.dto.leasingcontract.LeasingContractUpdateRequestDto;

public class LeasingContractUpdateRequestDtoStub {

    public static LeasingContractUpdateRequestDto getDto() {
        LeasingContractUpdateRequestDto responseDto = new LeasingContractUpdateRequestDto();
        responseDto.setContractNumber(151);
        responseDto.setMonthlyRate(1.5);

        return responseDto;
    }

}
