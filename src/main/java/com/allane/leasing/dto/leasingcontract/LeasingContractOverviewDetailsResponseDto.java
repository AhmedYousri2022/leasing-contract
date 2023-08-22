package com.allane.leasing.dto.leasingcontract;

import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LeasingContractOverviewDetailsResponseDto {

    private int contractNumber;

    private Double monthlyRate;

    private CustomerDetailsResponseDto customer;

    private VehicleDetailsResponseDto vehicle;
}
