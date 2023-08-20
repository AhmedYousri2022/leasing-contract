package com.allane.leasing.dto.leasingcontract;

import java.util.UUID;

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

    private UUID customerId;

    private UUID vehicleId;
}
