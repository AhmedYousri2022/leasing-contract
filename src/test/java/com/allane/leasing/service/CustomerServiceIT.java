package com.allane.leasing.service;

import java.util.UUID;

import com.allane.leasing.dto.customer.CustomerDetailsRequestDto;
import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.model.Customer;
import com.allane.leasing.repository.CustomerRepository;
import com.allane.leasing.stub.customer.CustomerDetailsRequestDtoStub;
import com.allane.leasing.stub.customer.CustomerModelStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerServiceIT {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerService service;

    @BeforeEach
    void cleanup() {
        repository.deleteAll();
    }

    @Test
    void shouldGetCustomerDetails() {
        Customer customer = repository.save(CustomerModelStub.getDto());

        CustomerDetailsResponseDto responseDto = service.getCustomerDetails(customer.getId());

        assertThat(responseDto.getFirstName(), is(customer.getFirstName()));
        assertThat(responseDto.getLastName(), is(customer.getLastName()));
        assertThat(responseDto.getBirthdate(), is(customer.getBirthdate()));
    }

    @Test
    void shouldAddCustomerDetails() {
        CustomerDetailsRequestDto requestDto = CustomerDetailsRequestDtoStub.getDto();

        CustomerDetailsResponseDto responseDto = service.addCustomerDetails(requestDto);

        assertThat(responseDto.getFirstName(), is(requestDto.getFirstName()));
        assertThat(responseDto.getLastName(), is(requestDto.getLastName()));
        assertThat(responseDto.getBirthdate(), is(requestDto.getBirthdate()));
    }

    @Test
    void shouldDeleteCustomerDetails() {
        Customer customer = repository.save(CustomerModelStub.getDto());

        service.deleteCustomerDetails(customer.getId());

        assertThat(repository.findAll(), hasSize(0));
    }

    @Test
    void shouldUpdateCustomerDetails() {
        Customer customer = repository.save(CustomerModelStub.getDto());
        CustomerDetailsRequestDto dto = CustomerDetailsRequestDtoStub.getDto();
        dto.setFirstName("Mo");

        CustomerDetailsResponseDto responseDto = service.updateCustomerDetails(customer.getId(), dto);

        assertThat(responseDto.getFirstName(), is(dto.getFirstName()));
        assertThat(responseDto.getLastName(), is(dto.getLastName()));
        assertThat(responseDto.getBirthdate(), is(dto.getBirthdate()));
    }

    @Test
    void shouldThrowCustomerNotfound() {
        Exception exception = assertThrows(
                NotFoundException.class,
                () -> service.updateCustomerDetails(UUID.fromString("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4"),
                                                    CustomerDetailsRequestDtoStub.getDto()),
                "Customer not found");

        assertThat(exception.getMessage(), is("Customer not found"));
    }
}
