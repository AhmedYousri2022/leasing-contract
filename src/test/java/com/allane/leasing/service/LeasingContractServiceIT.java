package com.allane.leasing.service;

import java.math.BigDecimal;
import java.util.List;

import com.allane.leasing.DatabaseContainer;
import com.allane.leasing.dto.leasingcontract.LeasingContractDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractOverviewDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractOverviewResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractRequestDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.exception.VehicleAssignedException;
import com.allane.leasing.model.Customer;
import com.allane.leasing.model.LeasingContract;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.repository.CustomerRepository;
import com.allane.leasing.repository.LeasingContractRepository;
import com.allane.leasing.repository.VehicleRepository;
import com.allane.leasing.stub.customer.CustomerModelStub;
import com.allane.leasing.stub.leasingcontract.LeasingContractModelDtoStub;
import com.allane.leasing.stub.leasingcontract.LeasingContractRequestDtoStub;
import com.allane.leasing.stub.vehicle.VehicleModelStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(initializers = {DatabaseContainer.class})
class LeasingContractServiceIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private LeasingContractRepository repository;

    @Autowired
    private LeasingContractService leasingContractService;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void shouldGetLeasingContractsOverview() {
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getModel());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getModel();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract leasingContract = repository.save(leasingContractStub);

        List<LeasingContractOverviewResponseDto> responseDtos = leasingContractService.getLeasingContractsOverview();

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
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getModel());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getModel();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract leasingContract = repository.save(leasingContractStub);

        LeasingContractOverviewDetailsResponseDto overviewDetails = leasingContractService
                .getLeasingContractsOverviewDetails(leasingContract.getId().toString());

        assertThat(overviewDetails.getContractNumber(), is(leasingContract.getContractNumber()));
    }

    @Test
    void shouldGetLeasingContractDetails() {
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getModel());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getModel();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract leasingContract = repository.save(leasingContractStub);

        LeasingContractDetailsResponseDto leasingContractDetails = leasingContractService.getLeasingContractDetails(
                leasingContract.getId().toString());

        assertThat(leasingContractDetails.getContractNumber(), is(leasingContract.getContractNumber()));
        assertThat(BigDecimal.valueOf(leasingContractDetails.getMonthlyRate()), is(leasingContract.getMonthlyRate()));
        assertThat(leasingContractDetails.getContractNumber(), is(leasingContract.getContractNumber()));
        assertThat(leasingContractDetails.getCustomerSummary(), is(leasingContract.getCustomer().getCustomerSummary()));
        assertThat(leasingContractDetails.getVehicleSummary(), is(leasingContract.getVehicle().getVehicleSummary()));
        assertThat(leasingContractDetails.getVehicleIdentificationNumber(),
                   is(leasingContract.getVehicle().getVehicleIdentificationNumber()));
    }

    @Test
    void shouldThrowContractNotFoundWhenGetLeasingContractDetails() {
        String nonExistentContractId = "5087fb1f-8d57-46e0-9cdb-ad70855f0fc4";

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> leasingContractService.getLeasingContractDetails(nonExistentContractId),
                "Contract not found");

        assertThat(exception.getMessage(), is("Contract not found"));
    }

    @Test
    void shouldCreateLeasingContract() {
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getModel());

        LeasingContractRequestDto dto = LeasingContractRequestDtoStub.getDto();
        dto.setVehicleId(vehicle.getId().toString());
        dto.setCustomerId(customer.getId().toString());

        LeasingContractDetailsResponseDto leasingContract = leasingContractService.createLeasingContract(dto);

        assertThat(leasingContract.getContractNumber(), is(dto.getContractNumber()));
        assertThat(leasingContract.getMonthlyRate(), is(dto.getMonthlyRate()));
        assertThat(leasingContract.getCustomerSummary(), is(customer.getCustomerSummary()));
        assertThat(leasingContract.getVehicleSummary(), is(vehicle.getVehicleSummary()));
        assertThat(leasingContract.getVehicleIdentificationNumber(), is(vehicle.getVehicleIdentificationNumber()));
    }

    @Test
    void shouldThrowVehicleAssignedExceptionWhenCreateLeasingContract() {
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicleModel = VehicleModelStub.getModel();
        vehicleModel.setAssigned(true);
        Vehicle vehicle = vehicleRepository.save(vehicleModel);

        LeasingContractRequestDto dto = LeasingContractRequestDtoStub.getDto();
        dto.setVehicleId(vehicle.getId().toString());
        dto.setCustomerId(customer.getId().toString());

        Exception exception = assertThrows(
                VehicleAssignedException.class,
                () -> leasingContractService.createLeasingContract(dto),
                "Vehicle already assigned to a contract");

        assertThat(exception.getMessage(), is("Vehicle already assigned to a contract"));
    }

    @Test
    void shouldUpdateContractNumberLeasingContract() {
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getModel());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getModel();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract saved = repository.save(leasingContractStub);
        LeasingContractRequestDto updatedLeasing = LeasingContractRequestDtoStub.getDto();
        updatedLeasing.setContractNumber(100);
        updatedLeasing.setVehicleId(vehicle.getId().toString());
        updatedLeasing.setCustomerId(customer.getId().toString());


        LeasingContractDetailsResponseDto leasingContract = leasingContractService.updateLeasingContract(saved.getId().toString(), updatedLeasing);

        assertThat(leasingContract.getContractNumber(), is(updatedLeasing.getContractNumber()));
        assertThat(leasingContract.getMonthlyRate(), is(updatedLeasing.getMonthlyRate()));
        assertThat(leasingContract.getCustomerSummary(), is(customer.getCustomerSummary()));
        assertThat(leasingContract.getVehicleSummary(), is(vehicle.getVehicleSummary()));
        assertThat(leasingContract.getVehicleIdentificationNumber(), is(vehicle.getVehicleIdentificationNumber()));
    }

    @Test
    void shouldUnassignVehicleLeasingContract() {
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getModel());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getModel();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract saved = repository.save(leasingContractStub);
        LeasingContractRequestDto updatedLeasing = LeasingContractRequestDtoStub.getDto();
        updatedLeasing.setContractNumber(100);
        updatedLeasing.setVehicleId(null);
        updatedLeasing.setCustomerId(customer.getId().toString());


        LeasingContractDetailsResponseDto leasingContract = leasingContractService.updateLeasingContract(saved.getId().toString(), updatedLeasing);

        assertThat(leasingContract.getContractNumber(), is(updatedLeasing.getContractNumber()));
        assertThat(leasingContract.getMonthlyRate(), is(updatedLeasing.getMonthlyRate()));
        assertThat(leasingContract.getCustomerSummary(), is(customer.getCustomerSummary()));
        assertThat(leasingContract.getVehicleSummary(), nullValue());
        assertThat(leasingContract.getVehicleIdentificationNumber(), is("-"));
    }

    @Test
    void shouldUnassignVehicleAndCustomerLeasingContract() {
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getModel());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getModel();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract saved = repository.save(leasingContractStub);
        LeasingContractRequestDto updatedLeasing = LeasingContractRequestDtoStub.getDto();
        updatedLeasing.setContractNumber(100);
        updatedLeasing.setVehicleId(null);
        updatedLeasing.setCustomerId(null);


        LeasingContractDetailsResponseDto leasingContract = leasingContractService.updateLeasingContract(saved.getId().toString(), updatedLeasing);

        assertThat(leasingContract.getContractNumber(), is(updatedLeasing.getContractNumber()));
        assertThat(leasingContract.getMonthlyRate(), is(updatedLeasing.getMonthlyRate()));
        assertThat(leasingContract.getCustomerSummary(), is(nullValue()));
        assertThat(leasingContract.getVehicleSummary(), nullValue());
        assertThat(leasingContract.getVehicleIdentificationNumber(), is("-"));
    }

    @Test
    void shouldThrowVehcileNotFoundWhenUpdateLeasingContract() {
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getModel());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getModel();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract saved = repository.save(leasingContractStub);
        LeasingContractRequestDto updatedLeasing = LeasingContractRequestDtoStub.getDto();


        Exception exception = assertThrows(
                NotFoundException.class,
                () -> leasingContractService.updateLeasingContract(saved.getId().toString(), updatedLeasing),
                "Vehicle not found");

        assertThat(exception.getMessage(), is("Vehicle not found"));
    }

    @Test
    void shouldThrowCustomerNotFoundWhenUpdateLeasingContract() {
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getModel());
        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getModel();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract saved = repository.save(leasingContractStub);
        LeasingContractRequestDto updatedLeasing = LeasingContractRequestDtoStub.getDto();
        updatedLeasing.setContractNumber(100);
        updatedLeasing.setCustomerId(customer.getId().toString());

        Exception exception = assertThrows(
                NotFoundException.class,
                () -> leasingContractService.updateLeasingContract(saved.getId().toString(), updatedLeasing),
                "Vehicle not found");

        assertThat(exception.getMessage(), is("Vehicle not found"));
    }

    @Test
    void shouldThrowAssociatedExceptionWhenUpdateLeasingContract() {
        Customer customer = customerRepository.save(CustomerModelStub.getModel());
        Vehicle vehicle = vehicleRepository.save(VehicleModelStub.getModel());
        Vehicle vehicleModel = VehicleModelStub.getModel();
        vehicleModel.setAssigned(true);
        Vehicle vehicle2 = vehicleRepository.save(vehicleModel);

        LeasingContract leasingContractStub = LeasingContractModelDtoStub.getModel();
        leasingContractStub.setVehicle(vehicle);
        leasingContractStub.setCustomer(customer);
        LeasingContract saved = repository.save(leasingContractStub);
        LeasingContractRequestDto updatedLeasing = LeasingContractRequestDtoStub.getDto();
        updatedLeasing.setContractNumber(100);
        updatedLeasing.setCustomerId(customer.getId().toString());
        updatedLeasing.setVehicleId(vehicle2.getId().toString());


        Exception exception = assertThrows(
                VehicleAssignedException.class,
                () -> leasingContractService.updateLeasingContract(saved.getId().toString(), updatedLeasing),
                "Vehicle already assigned to a contract");

        assertThat(exception.getMessage(), is("Vehicle already assigned to a contract"));
    }
}
