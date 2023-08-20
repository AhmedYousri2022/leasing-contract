package com.allane.leasing.controller;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.allane.leasing.dto.customer.CustomerDetailsRequestDto;
import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequiredArgsConstructor
@RequestMapping("/customers")
//@Api(value = "leasing service", tags = {"contracts"})
public class CustomerController {

    private final CustomerService service;

    //    @ApiOperation(value = "Add new Person")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDetailsResponseDto addCustomer(@Valid @RequestBody CustomerDetailsRequestDto dto) {
        return service.addCustomerDetails(dto);
    }


    //    @ApiOperation(value = "get person details")
    @GetMapping(path = "/{customerId}")
    public CustomerDetailsResponseDto getCustomer(@NotNull @PathVariable UUID customerId) {
        return service.getCustomerDetails(customerId);
    }

    //    @ApiOperation(value = "Change a name of a person")
    @PutMapping(path = "/{customerId}")
    public CustomerDetailsResponseDto updateCustomer(@NotNull @PathVariable UUID customerId,
                                                     @Valid @RequestBody CustomerDetailsRequestDto dto) {
        return service.updateCustomerDetails(customerId, dto);
    }


    //    @ApiOperation(value = "delete person")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{customerId}")
    public void deleteCustomer(@Valid @PathVariable UUID customerId) {
        service.deleteCustomerDetails(customerId);
    }
}
