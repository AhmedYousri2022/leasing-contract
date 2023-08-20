package com.allane.leasing.stub.customer;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import com.allane.leasing.model.Customer;

public class CustomerModelStub {

    public static Customer getDto() {

        Customer customer = new Customer();
        customer.setId(UUID.fromString("eab78474-3329-42a1-b8b8-b13efd3c5572"));
        customer.setFirstName("Ahmed");
        customer.setLastName("Salama");
        customer.setBirthdate(LocalDate.of(1990, Month.MAY, 10));

        return customer;
    }
}
