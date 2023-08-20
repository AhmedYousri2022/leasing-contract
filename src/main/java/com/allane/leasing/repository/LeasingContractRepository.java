package com.allane.leasing.repository;

import java.util.Optional;
import java.util.UUID;

import com.allane.leasing.model.LeasingContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeasingContractRepository extends JpaRepository<LeasingContract, UUID> {

    Optional<LeasingContract> findLeasingContractByContractNumber(int contractNumber);
    Optional<LeasingContract> findLeasingContractByVehicleId(UUID vehicleId);
    Optional<LeasingContract> findLeasingContractByContractNumberAndVehicleId(int contractNumber, UUID vehicleId);
    Optional<LeasingContract> findLeasingContractByContractNumberAndCustomerId(int contractNumber, UUID customerId);


}
