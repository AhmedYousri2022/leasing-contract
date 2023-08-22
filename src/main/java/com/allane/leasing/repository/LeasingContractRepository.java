package com.allane.leasing.repository;

import java.util.UUID;

import com.allane.leasing.model.LeasingContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeasingContractRepository extends JpaRepository<LeasingContract, UUID> {

}
