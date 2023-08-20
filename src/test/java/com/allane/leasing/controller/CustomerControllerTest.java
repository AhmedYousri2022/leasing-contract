package com.allane.leasing.controller;

import java.util.UUID;

import com.allane.leasing.dto.customer.CustomerDetailsRequestDto;
import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.service.CustomerService;
import com.allane.leasing.stub.customer.CustomerDetailsRequestDtoStub;
import com.allane.leasing.stub.customer.CustomerDetailsResponseDtoStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService service;

    private CustomerDetailsRequestDto customerRequestDto;

    @BeforeEach
    void setup() {
        customerRequestDto = CustomerDetailsRequestDtoStub.getDto();
    }

    @Test
    void shouldAddCustomer() throws Exception {
        CustomerDetailsResponseDto dto = CustomerDetailsResponseDtoStub.getDto();
        given(service.addCustomerDetails(customerRequestDto)).willReturn(dto);

        mvc.perform(post("/customers")
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerRequestDto)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(dto.getLastName()))
                .andExpect(jsonPath("$.birthdate").value(dto.getBirthdate().toString()));
    }

    @Test
    void shouldGetCustomer() throws Exception {
        String customerId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        CustomerDetailsResponseDto dto = CustomerDetailsResponseDtoStub.getDto();
        given(service.getCustomerDetails(UUID.fromString(customerId)))
                .willReturn(dto);

        mvc.perform(get("/customers/" + customerId)
                            .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(dto.getLastName()))
                .andExpect(jsonPath("$.birthdate").value(dto.getBirthdate().toString()));
    }

    @Test
    void shouldThrowCustomerNotFound() throws Exception {
        String customerId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doThrow(new NotFoundException("Customer not found"))
                .when(service)
                .getCustomerDetails(UUID.fromString(customerId));

        mvc.perform(get("/customers/" + customerId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.zonedDateTime").exists())
                .andExpect(jsonPath("$.message").value("Customer not found"));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        String customerId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        CustomerDetailsResponseDto dto = CustomerDetailsResponseDtoStub.getDto();
        customerRequestDto.setFirstName("Mido");

        given(service.updateCustomerDetails(UUID.fromString(customerId), customerRequestDto)).willReturn(dto);

        mvc.perform(put("/customers/" + customerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerRequestDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(dto.getLastName()))
                .andExpect(jsonPath("$.birthdate").value(dto.getBirthdate().toString()));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        String customerId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        doNothing().when(service).deleteCustomerDetails(UUID.fromString(customerId));

        mvc.perform(delete("/customers/" + customerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isNoContent());
    }
}
