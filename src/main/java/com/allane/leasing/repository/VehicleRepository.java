package com.allane.leasing.repository;

import java.util.List;
import java.util.UUID;

import com.allane.leasing.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    List<Vehicle> findByAssigned(boolean assigned);
}
