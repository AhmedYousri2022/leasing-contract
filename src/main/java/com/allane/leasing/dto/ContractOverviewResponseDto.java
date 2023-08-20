package com.allane.leasing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ContractOverviewResponseDto {

    private int contractNumber;

    private String customerSummary;

    private String vehicleSummary;

    private String vehicleIdentificationNumber;

    private Double monthlyRate;

    private Double vehiclePrice;
}
