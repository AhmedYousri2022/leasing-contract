package com.allane.leasing.controller;

import java.util.List;
import java.util.UUID;

import com.allane.leasing.dto.leasingcontract.ContractOverviewDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.ContractOverviewResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractRequestDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractUpdateRequestDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.service.LeasingContractService;
import com.allane.leasing.stub.leasingcontract.ContractOverviewDetailsResponseDtoStub;
import com.allane.leasing.stub.leasingcontract.ContractOverviewResponseDtoStub;
import com.allane.leasing.stub.leasingcontract.LeasingContractDetailsResponseDtoStub;
import com.allane.leasing.stub.leasingcontract.LeasingContractRequestDtoStub;
import com.allane.leasing.stub.leasingcontract.LeasingContractUpdateRequestDtoStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LeasingContractController.class)
class LeasingContractControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LeasingContractService leasingContractService;

    @Test
    void shouldGetLeasingContractsOverview() throws Exception {
        ContractOverviewResponseDto responseDto = ContractOverviewResponseDtoStub.getDto();

        given(leasingContractService.getLeasingContractsOverview()).willReturn(List.of(responseDto));

        mvc.perform(get("/contracts")
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contractNumber").value(responseDto.getContractNumber()))
                .andExpect(jsonPath("$[0].monthlyRate").value(responseDto.getMonthlyRate()))
                .andExpect(jsonPath("$[0].customerSummary").value(responseDto.getCustomerSummary()))
                .andExpect(jsonPath("$[0].vehicleSummary").value(responseDto.getVehicleSummary()))
                .andExpect(jsonPath("$[0].vehicleIdentificationNumber").value(responseDto.getVehicleIdentificationNumber()));
    }

    @Test
    void shouldGetLeasingContractOverviewDetails() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        ContractOverviewDetailsResponseDto responseDto = ContractOverviewDetailsResponseDtoStub.getDto();

        given(leasingContractService.getLeasingContractsOverviewDetails(UUID.fromString(leasingContractId)))
                .willReturn(responseDto);

        mvc.perform(get("/contracts/details/" + leasingContractId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractNumber").value(responseDto.getContractNumber()))
                .andExpect(jsonPath("$.monthlyRate").value(responseDto.getMonthlyRate()))
                .andExpect(jsonPath("$.customer.firstName").value(responseDto.getCustomer().getFirstName()))
                .andExpect(jsonPath("$.customer.lastName").value(responseDto.getCustomer().getLastName()))
                .andExpect(jsonPath("$.customer.birthdate").value(responseDto.getCustomer().getBirthdate().toString()))
                .andExpect(jsonPath("$.vehicle.brand").value(responseDto.getVehicle().getBrand()))
                .andExpect(jsonPath("$.vehicle.model").value(responseDto.getVehicle().getModel()))
                .andExpect(jsonPath("$.vehicle.modelYear").value(responseDto.getVehicle().getModelYear()))
                .andExpect(jsonPath("$.vehicle.vehicleIdentificationNumber").value(
                        responseDto.getVehicle().getVehicleIdentificationNumber()))
                .andExpect(jsonPath("$.vehicle.price").value(responseDto.getVehicle().getPrice()));
    }

    @Test
    void shouldGetLeasingContract() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        LeasingContractDetailsResponseDto responseDto = LeasingContractDetailsResponseDtoStub.getDto();

        given(leasingContractService.getLeasingContractDetails(UUID.fromString(leasingContractId)))
                .willReturn(responseDto);

        mvc.perform(get("/contracts/" + leasingContractId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.contractNumber").value(responseDto.getContractNumber()))
                .andExpect(jsonPath("$.monthlyRate").value(responseDto.getMonthlyRate()))
                .andExpect(jsonPath("$.customerSummary").value(responseDto.getCustomerSummary()))
                .andExpect(jsonPath("$.vehicleSummary").value(responseDto.getVehicleSummary()))
                .andExpect(jsonPath("$.vehicleIdentificationNumber").value(responseDto.getVehicleIdentificationNumber()));
    }

    @Test
    void shouldCreateLeasingContract() throws Exception {
        LeasingContractRequestDto requestDto = LeasingContractRequestDtoStub.getDto();

        LeasingContractDetailsResponseDto responseDto = LeasingContractDetailsResponseDtoStub.getDto();

        given(leasingContractService.createLeasingContract(requestDto)).willReturn(responseDto);

        mvc.perform(post("/contracts")
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.contractNumber").value(responseDto.getContractNumber()))
                .andExpect(jsonPath("$.monthlyRate").value(responseDto.getMonthlyRate()))
                .andExpect(jsonPath("$.customerSummary").value(responseDto.getCustomerSummary()))
                .andExpect(jsonPath("$.vehicleSummary").value(responseDto.getVehicleSummary()))
                .andExpect(jsonPath("$.vehicleIdentificationNumber").value(responseDto.getVehicleIdentificationNumber()));
    }

    @Test
    void shouldThrowCustomerNotFoundWhenCreateLeasingContract() throws Exception {
        LeasingContractRequestDto requestDto = LeasingContractRequestDtoStub.getDto();

        doThrow(new NotFoundException("Customer not found"))
                .when(leasingContractService)
                .createLeasingContract(requestDto);

        mvc.perform(post("/contracts")
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.zonedDateTime").exists())
                .andExpect(jsonPath("$.message").value("Customer not found"));
    }

    @Test
    void shouldThrowVehicleNotFoundWhenCreateLeasingContract() throws Exception {
        LeasingContractRequestDto requestDto = LeasingContractRequestDtoStub.getDto();

        doThrow(new NotFoundException("Vehicle not found"))
                .when(leasingContractService)
                .createLeasingContract(requestDto);

        mvc.perform(post("/contracts")
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.zonedDateTime").exists())
                .andExpect(jsonPath("$.message").value("Vehicle not found"));
    }

    @Test
    void shouldAssignVehicle() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        String vehicleId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doNothing().when(leasingContractService)
                .assignVehicle(UUID.fromString(leasingContractId), UUID.fromString(vehicleId));

        mvc.perform(post("/contracts/assign/" + leasingContractId + "/vehicle/" + vehicleId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowNotFoundWhenNoContractFoundAssignVehicle() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        String vehicleId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doThrow(new NotFoundException("Contract not found"))
                .when(leasingContractService)
                .assignVehicle(UUID.fromString(leasingContractId), UUID.fromString(vehicleId));

        mvc.perform(post("/contracts/assign/" + leasingContractId + "/vehicle/" + vehicleId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.zonedDateTime").exists())
                .andExpect(jsonPath("$.message").value("Contract not found"));
    }

    @Test
    void shouldThrowNotFoundWhenNoVehicleFoundAssign() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        String vehicleId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doThrow(new NotFoundException("Vehicle not found"))
                .when(leasingContractService)
                .assignVehicle(UUID.fromString(leasingContractId), UUID.fromString(vehicleId));

        mvc.perform(post("/contracts/assign/" + leasingContractId + "/vehicle/" + vehicleId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.zonedDateTime").exists())
                .andExpect(jsonPath("$.message").value("Vehicle not found"));
    }

    @Test
    void shouldUnAssignVehicle() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        String vehicleId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doNothing().when(leasingContractService).unAssignVehicle(UUID.fromString(leasingContractId),
                                                                 UUID.fromString(vehicleId));

        mvc.perform(post("/contracts/unassign/" + leasingContractId + "/vehicle/" + vehicleId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isOk());
    }

    @Test
    void shouldAssignCustomer() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        String customerId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doNothing().when(leasingContractService)
                .assignCustomer(UUID.fromString(leasingContractId), UUID.fromString(customerId));

        mvc.perform(post("/contracts/assign/" + leasingContractId + "/customer/" + customerId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowNotFoundWhenNoContractFound() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        String customerId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doThrow(new NotFoundException("Contract not found"))
                .when(leasingContractService)
                .assignCustomer(UUID.fromString(leasingContractId), UUID.fromString(customerId));

        mvc.perform(post("/contracts/assign/" + leasingContractId + "/customer/" + customerId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.zonedDateTime").exists())
                .andExpect(jsonPath("$.message").value("Contract not found"));
    }

    @Test
    void shouldThrowNotFoundWhenNoCustomer() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        String customerId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doThrow(new NotFoundException("Customer not found"))
                .when(leasingContractService)
                .assignCustomer(UUID.fromString(leasingContractId), UUID.fromString(customerId));

        mvc.perform(post("/contracts/assign/" + leasingContractId + "/customer/" + customerId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.zonedDateTime").exists())
                .andExpect(jsonPath("$.message").value("Customer not found"));
    }

    @Test
    void shouldUnAssignCustomer() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        String customerId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doNothing()
                .when(leasingContractService)
                .unAssignVehicle(UUID.fromString(leasingContractId),
                                 UUID.fromString(customerId));

        mvc.perform(post("/contracts/unassign/" + leasingContractId + "/customer/" + customerId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowNotFoundCustomerWhenUnAssignCustomer() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        String customerId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doThrow(new NotFoundException("Customer not found"))
                .when(leasingContractService)
                .unAssignCustomer(UUID.fromString(leasingContractId), UUID.fromString(customerId));

        mvc.perform(post("/contracts/unassign/" + leasingContractId + "/customer/" + customerId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.zonedDateTime").exists())
                .andExpect(jsonPath("$.message").value("Customer not found"));
    }

    @Test
    void shouldThrowNotFoundContractWhenUnAssignCustomer() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        String customerId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doThrow(new NotFoundException("Contract not found"))
                .when(leasingContractService)
                .unAssignCustomer(UUID.fromString(leasingContractId), UUID.fromString(customerId));

        mvc.perform(post("/contracts/unassign/" + leasingContractId + "/customer/" + customerId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.zonedDateTime").exists())
                .andExpect(jsonPath("$.message").value("Contract not found"));
    }

    @Test
    void shouldUpdateLeasingContract() throws Exception {
        String leasingContractId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        LeasingContractUpdateRequestDto dto = LeasingContractUpdateRequestDtoStub.getDto();
        LeasingContractDetailsResponseDto responseDto = LeasingContractDetailsResponseDtoStub.getDto();
        responseDto.setContractNumber(152);

        given(leasingContractService.updateLeasingContract(UUID.fromString(leasingContractId), dto))
                .willReturn(responseDto);

        mvc.perform(put("/contracts/" + leasingContractId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.contractNumber").value(152))
                .andExpect(jsonPath("$.monthlyRate").value(dto.getMonthlyRate().toString()));
    }
}
