package com.allane.leasing.stub.vehicle;

import java.util.UUID;

import com.allane.leasing.model.Vehicle;

public class VehicleModelStub {

    public static Vehicle getDto() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.fromString("eab78474-3329-42a1-b8b8-b13efd3c5572"));
        vehicle.setBrand("X1");
        vehicle.setModel("BMW");
        vehicle.setAssigned(false);
        vehicle.setModelYear("2023");
        vehicle.setPrice(125000d);
        vehicle.setVehicleIdentificationNumber("125-152");

        return vehicle;
    }
}
