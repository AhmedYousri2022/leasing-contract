package com.allane.leasing.dto.leasingcontract;

import com.allane.leasing.validation.ValidUUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class LeasingContractRequestDto {

    private int contractNumber;

    private Double monthlyRate;

    @ValidUUID
    private String customerId;

    @ValidUUID
    private String vehicleId;
}
