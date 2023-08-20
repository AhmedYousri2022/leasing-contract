package com.allane.leasing.service;

import java.math.BigDecimal;
import java.util.List;

import com.allane.leasing.dto.leasingcontract.ContractOverviewDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.ContractOverviewResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractDetailsResponseDto;
import com.allane.leasing.model.Customer;
import com.allane.leasing.model.LeasingContract;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.repository.CustomerRepository;
import com.allane.leasing.repository.LeasingContractRepository;
import com.allane.leasing.repository.VehicleRepository;
import com.allane.leasing.stub.customer.CustomerModelStub;
import com.allane.leasing.stub.leasingcontract.LeasingContractModelDtoStub;
import com.allane.leasing.stub.vehicle.VehicleModelStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class ContractLeasingServiceIT {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private LeasingContractRepository repository;

    @Autowired
    private LeasingContractService leasingContractService;


    @BeforeEach
    void cleanup() {
        repository.deleteAll();
    }

    @Test
    void shouldGetLeasingContractsOverview() {
        Customer customer = customerRepository.save(CustomerModelStub.getDto());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getDto());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getDto();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract leasingContract = repository.save(leasingContractStub);

        List<ContractOverviewResponseDto> responseDtos = leasingContractService.getLeasingContractsOverview();

        assertThat(responseDtos.get(0).getContractNumber(), is(leasingContract.getContractNumber()));
        assertThat(BigDecimal.valueOf(responseDtos.get(0).getMonthlyRate()), is(leasingContract.getMonthlyRate()));
        assertThat(responseDtos.get(0).getVehiclePrice(), is(leasingContract.getVehicle().getPrice()));
        assertThat(responseDtos.get(0).getCustomerSummary(), is(leasingContract.getCustomer().getCustomerSummary()));
        assertThat(responseDtos.get(0).getVehicleSummary(), is(leasingContract.getVehicle().getVehicleSummary()));
        assertThat(responseDtos.get(0).getVehicleIdentificationNumber(),
                   is(leasingContract.getVehicle().getVehicleIdentificationNumber()));
    }

    @Test
    void shouldGetLeasingContractsOverviewDetails() {
        Customer customer = customerRepository.save(CustomerModelStub.getDto());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getDto());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getDto();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract leasingContract = repository.save(leasingContractStub);

        ContractOverviewDetailsResponseDto overviewDetails = leasingContractService
                .getLeasingContractsOverviewDetails(leasingContract.getId());

        assertThat(overviewDetails.getContractNumber(), is(leasingContract.getContractNumber()));
        assertThat(BigDecimal.valueOf(overviewDetails.getMonthlyRate()), is(leasingContract.getMonthlyRate()));
        assertThat(overviewDetails.getVehicle().getPrice(), is(leasingContract.getVehicle().getPrice()));
        assertThat(overviewDetails.getVehicle().getBrand(), is(leasingContract.getVehicle().getBrand()));
        assertThat(overviewDetails.getVehicle().getModelYear(), is(leasingContract.getVehicle().getModelYear()));
        assertThat(overviewDetails.getVehicle().getVehicleIdentificationNumber(),
                   is(leasingContract.getVehicle().getVehicleIdentificationNumber()));
        assertThat(overviewDetails.getCustomer().getFirstName(), is(leasingContract.getCustomer().getFirstName()));
        assertThat(overviewDetails.getCustomer().getLastName(), is(leasingContract.getCustomer().getLastName()));
        assertThat(overviewDetails.getCustomer().getBirthdate(), is(leasingContract.getCustomer().getBirthdate()));
    }

    @Test
    void shouldGetLeasingContractDetails() {
        Customer customer = customerRepository.save(CustomerModelStub.getDto());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getDto());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getDto();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract leasingContract = repository.save(leasingContractStub);

        LeasingContractDetailsResponseDto leasingContractDetails = leasingContractService.getLeasingContractDetails(
                leasingContract.getId());

        assertThat(leasingContractDetails.getContractNumber(), is(leasingContract.getContractNumber()));
        assertThat(BigDecimal.valueOf(leasingContractDetails.getMonthlyRate()), is(leasingContract.getMonthlyRate()));
        assertThat(leasingContractDetails.getContractNumber(), is(leasingContract.getContractNumber()));
        assertThat(leasingContractDetails.getCustomerSummary(), is(leasingContract.getCustomer().getCustomerSummary()));
        assertThat(leasingContractDetails.getVehicleSummary(), is(leasingContract.getVehicle().getVehicleSummary()));
        assertThat(leasingContractDetails.getVehicleIdentificationNumber(),
                   is(leasingContract.getVehicle().getVehicleIdentificationNumber()));
    }


}
