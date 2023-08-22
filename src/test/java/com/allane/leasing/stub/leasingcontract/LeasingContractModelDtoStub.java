package com.allane.leasing.stub.leasingcontract;

import java.math.BigDecimal;

import com.allane.leasing.model.LeasingContract;
import com.allane.leasing.stub.customer.CustomerModelStub;
import com.allane.leasing.stub.vehicle.VehicleModelStub;

public class LeasingContractModelDtoStub {

    public static LeasingContract getModel() {
        LeasingContract leasingContract = new LeasingContract();

        leasingContract.setContractNumber(151);
        leasingContract.setMonthlyRate(new BigDecimal("1.5"));
        leasingContract.setCustomer(CustomerModelStub.getModel());
        leasingContract.setVehicle(VehicleModelStub.getModel());

        return leasingContract;
    }
}
