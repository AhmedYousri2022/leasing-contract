package com.allane.leasing.controller;

import java.util.UUID;

import com.allane.leasing.dto.leasingcontract.LeasingContractDetailsResponseDto;
import com.allane.leasing.dto.leasingcontract.LeasingContractRequestDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.service.CustomerService;
import com.allane.leasing.service.LeasingContractService;
import com.allane.leasing.service.VehicleService;
import com.allane.leasing.stub.leasingcontract.LeasingContractDetailsResponseDtoStub;
import com.allane.leasing.stub.leasingcontract.LeasingContractRequestDtoStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LeasingContractController.class)
class LeasingContractControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private VehicleService vehicleService;

    @MockBean
    private LeasingContractService leasingContractService;

    @Test
    void shouldGetLeasingContractsOverview() {
    }

    @Test
    void shouldGetLeasingContractsOverviewDetails() {
    }

    @Test
    void shouldGetLeasingContract() {
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
    void shouldAssignVehicle() {
    }

    @Test
    void shouldUnAssignVehicle() {
    }

    @Test
    void shouldAssignCustomer() {
    }

    @Test
    void shouldUnAssignCustomer() {
    }

    @Test
    void shouldUpdateLeasingContract() {
    }
}
