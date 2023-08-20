package com.allane.leasing.dto.leasingcontract;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LeasingContractDetailsResponseDto {

    private UUID id;

    private int contractNumber;

    private Double monthlyRate;

    private String customerSummary;

    private String vehicleSummary;

    private String vehicleIdentificationNumber;
}
