package com.allane.leasing.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private String modelYear;

    @NotNull
    private boolean assigned = false;

    @Column
    private String vehicleIdentificationNumber;

    @NotNull
    private Double price;

    @OneToOne(mappedBy = "vehicle")
    private LeasingContract leasingContract;

    public String getVehicleSummary() {
        if (getBrand() != null && getModel() != null && getModelYear() != null) {
            return getBrand() + " " + getModel() + " (" + getModelYear() + ")";
        }
        return "";
    }
}
