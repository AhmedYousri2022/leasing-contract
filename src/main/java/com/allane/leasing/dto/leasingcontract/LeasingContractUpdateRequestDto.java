package com.allane.leasing.dto.leasingcontract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LeasingContractUpdateRequestDto {

    private int contractNumber;

    private Double monthlyRate;
}
