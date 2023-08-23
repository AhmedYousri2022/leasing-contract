package com.allane.leasing.service;

import com.allane.leasing.DatabaseContainer;
import com.allane.leasing.dto.customer.CustomerDetailsRequestDto;
import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.model.Customer;
import com.allane.leasing.repository.CustomerRepository;
import com.allane.leasing.stub.customer.CustomerDetailsRequestDtoStub;
import com.allane.leasing.stub.customer.CustomerModelStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(initializers = {DatabaseContainer.class})
class CustomerServiceIT {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerService service;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void shouldGetCustomerDetails() {
        Customer customer = repository.save(CustomerModelStub.getModel());

        CustomerDetailsResponseDto responseDto = service.getCustomerDetails(customer.getId().toString());

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
        Customer customer = repository.save(CustomerModelStub.getModel());

        service.deleteCustomerDetails(customer.getId().toString());

        assertThat(repository.findAll(), hasSize(0));
    }

    @Test
    void shouldUpdateCustomerDetails() {
        Customer customer = repository.save(CustomerModelStub.getModel());
        CustomerDetailsRequestDto dto = CustomerDetailsRequestDtoStub.getDto();
        dto.setFirstName("Mo");

        CustomerDetailsResponseDto responseDto = service.updateCustomerDetails(customer.getId().toString(), dto);

        assertThat(responseDto.getFirstName(), is(dto.getFirstName()));
        assertThat(responseDto.getLastName(), is(dto.getLastName()));
        assertThat(responseDto.getBirthdate(), is(dto.getBirthdate()));
    }

    @Test
    void shouldThrowCustomerNotfound() {
        Exception exception = assertThrows(
                NotFoundException.class,
                () -> service.updateCustomerDetails("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4", CustomerDetailsRequestDtoStub.getDto()),
                "Customer not found");

        assertThat(exception.getMessage(), is("Customer not found"));
    }
}
