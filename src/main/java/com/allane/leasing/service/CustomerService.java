package com.allane.leasing.service;

import java.util.UUID;

import com.allane.leasing.dto.customer.CustomerDetailsRequestDto;
import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.mapper.CustomerMapper;
import com.allane.leasing.model.Customer;
import com.allane.leasing.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    public CustomerDetailsResponseDto getCustomerDetails(UUID customerId) {
        Customer customer = getCustomer(customerId);
        return customerMapper.toDto(customer);
    }

    public CustomerDetailsResponseDto addCustomerDetails(CustomerDetailsRequestDto dto) {
        Customer customer = customerRepository.save(customerMapper.toCustomer(dto));
        return customerMapper.toDto(customer);
    }

    public void deleteCustomerDetails(UUID customerId) {
        Customer customer = getCustomer(customerId);
        customerRepository.delete(customer);
    }

    public CustomerDetailsResponseDto updateCustomerDetails(UUID customerId, CustomerDetailsRequestDto dto) {
        Customer customer = getCustomer(customerId);
        customerMapper.updateCustomerFromDto(dto, customer);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toDto(saved);
    }

    public Customer getCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    }
}
