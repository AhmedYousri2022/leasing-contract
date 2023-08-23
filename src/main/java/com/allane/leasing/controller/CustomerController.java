package com.allane.leasing.controller;

import javax.validation.Valid;

import com.allane.leasing.dto.customer.CustomerDetailsRequestDto;
import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.service.CustomerService;
import com.allane.leasing.validation.ValidUUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDetailsResponseDto addCustomer(@Valid @RequestBody CustomerDetailsRequestDto dto) {
        return service.addCustomerDetails(dto);
    }

    @GetMapping(path = "/{customerId}")
    public CustomerDetailsResponseDto getCustomer(@ValidUUID @PathVariable String customerId) {
        return service.getCustomerDetails(customerId);
    }

    @PutMapping(path = "/{customerId}")
    public CustomerDetailsResponseDto updateCustomer(@ValidUUID @PathVariable String customerId,
                                                     @Valid @RequestBody CustomerDetailsRequestDto dto) {
        return service.updateCustomerDetails(customerId, dto);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{customerId}")
    public void deleteCustomer(@ValidUUID @PathVariable String customerId) {
        service.deleteCustomerDetails(customerId);
    }
}
