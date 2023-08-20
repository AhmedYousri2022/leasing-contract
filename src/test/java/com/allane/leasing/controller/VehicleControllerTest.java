package com.allane.leasing.controller;

import java.util.UUID;

import com.allane.leasing.dto.vehicle.VehicleDetailsRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.service.VehicleService;
import com.allane.leasing.stub.vehicle.VehicleDetailsRequestDtoStub;
import com.allane.leasing.stub.vehicle.VehicleDetailsResponseDtoStub;
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

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleService service;

    private VehicleDetailsRequestDto vehicleRequestDto;

    @BeforeEach
    void setup() {
        vehicleRequestDto = VehicleDetailsRequestDtoStub.getDto();
    }

    @Test
    void shouldAddVehicle() throws Exception {
        VehicleDetailsResponseDto dto = VehicleDetailsResponseDtoStub.getDto();
        given(service.addVehicleDetails(vehicleRequestDto)).willReturn(dto);

        mvc.perform(post("/vehicles")
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vehicleRequestDto)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.vehicleIdentificationNumber").value(dto.getVehicleIdentificationNumber()))
                .andExpect(jsonPath("$.brand").value(dto.getBrand()))
                .andExpect(jsonPath("$.price").value(dto.getPrice().toString()))
                .andExpect(jsonPath("$.model").value(dto.getModel()))
                .andExpect(jsonPath("$.modelYear").value(dto.getModelYear()));
    }

    @Test
    void shouldGetVehicle() throws Exception {
        String vehicleId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        VehicleDetailsResponseDto dto = VehicleDetailsResponseDtoStub.getDto();
        given(service.getVehicleDetails(UUID.fromString(vehicleId)))
                .willReturn(dto);

        mvc.perform(get("/vehicles/" + vehicleId)
                            .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.vehicleIdentificationNumber").value(dto.getVehicleIdentificationNumber()))
                .andExpect(jsonPath("$.brand").value(dto.getBrand()))
                .andExpect(jsonPath("$.price").value(dto.getPrice().toString()))
                .andExpect(jsonPath("$.model").value(dto.getModel()))
                .andExpect(jsonPath("$.modelYear").value(dto.getModelYear()));
    }

    @Test
    void shouldThrowVehicleNotFound() throws Exception {
        String vehicleId = "eab78474-3329-42a1-b8b8-b13efd3c5572";

        doThrow(new NotFoundException("Vehicle not found"))
                .when(service)
                .getVehicleDetails(UUID.fromString(vehicleId));

        mvc.perform(get("/vehicles/" + vehicleId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.zonedDateTime").exists())
                .andExpect(jsonPath("$.message").value("Vehicle not found"));
    }

    @Test
    void shouldUpdateVehicle() throws Exception {
        String vehicleId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        VehicleDetailsResponseDto dto = VehicleDetailsResponseDtoStub.getDto();
        dto.setBrand("BMW");
        vehicleRequestDto.setModel("BMW");

        given(service.updateVehicleDetails(UUID.fromString(vehicleId), vehicleRequestDto)).willReturn(dto);

        mvc.perform(put("/vehicles/" + vehicleId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vehicleRequestDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.vehicleIdentificationNumber").value(dto.getVehicleIdentificationNumber()))
                .andExpect(jsonPath("$.brand").value(dto.getBrand()))
                .andExpect(jsonPath("$.price").value(dto.getPrice().toString()))
                .andExpect(jsonPath("$.model").value(dto.getModel()))
                .andExpect(jsonPath("$.modelYear").value(dto.getModelYear()));
    }

    @Test
    void shouldDeleteVehicle() throws Exception {
        String vehicleId = "eab78474-3329-42a1-b8b8-b13efd3c5572";
        doNothing().when(service).deleteVehicleDetails(UUID.fromString(vehicleId));

        mvc.perform(delete("/vehicles/" + vehicleId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isNoContent());
    }
}
