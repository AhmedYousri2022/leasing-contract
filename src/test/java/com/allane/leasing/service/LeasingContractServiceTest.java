package com.allane.leasing.service;

import java.util.Optional;
import java.util.UUID;

import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.exception.NotFoundException;
import com.allane.leasing.model.Customer;
import com.allane.leasing.repository.CustomerRepository;
import com.allane.leasing.stub.customer.CustomerDetailsRequestDtoStub;
import com.allane.leasing.stub.customer.CustomerModelStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeasingContractServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @Test
    void shouldGetCustomerDetails() {
        Customer customer = CustomerModelStub.getDto();
        when(repository.findById(any())).thenReturn(Optional.of(customer));

        CustomerDetailsResponseDto customerDetails = service.getCustomerDetails(CustomerModelStub.getDto().getId());

        assertThat(customerDetails.getFirstName(), is(customer.getFirstName()));
        assertThat(customerDetails.getLastName(), is(customer.getLastName()));
        assertThat(customerDetails.getBirthdate(), is(customer.getBirthdate()));
    }

    @Test
    void shouldAddCustomerDetails() {
        Customer customer = CustomerModelStub.getDto();
        when(repository.save(any())).thenReturn(customer);

        CustomerDetailsResponseDto customerDetailsResponseDto = service.addCustomerDetails(
                CustomerDetailsRequestDtoStub.getDto());

        assertThat(customerDetailsResponseDto.getFirstName(), is(customer.getFirstName()));
        assertThat(customerDetailsResponseDto.getLastName(), is(customer.getLastName()));
        assertThat(customerDetailsResponseDto.getBirthdate(), is(customer.getBirthdate()));
    }

    @Test
    void shouldUpdateCustomerDetails() {
        Customer customer = CustomerModelStub.getDto();
        Customer updated = CustomerModelStub.getDto();
        updated.setFirstName("Mo");

        when(repository.findById(any())).thenReturn(Optional.of(customer));
        when(repository.save(any())).thenReturn(updated);

        CustomerDetailsResponseDto customerDetailsResponseDto = service
                .updateCustomerDetails(customer.getId(),  CustomerDetailsRequestDtoStub.getDto());

        assertThat(customerDetailsResponseDto.getFirstName(), is(updated.getFirstName()));
        assertThat(customerDetailsResponseDto.getLastName(), is(updated.getLastName()));
        assertThat(customerDetailsResponseDto.getBirthdate(), is(updated.getBirthdate()));
    }

    @Test
    void shouldThrowCustomerNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                                                   () -> service.deleteCustomerDetails(UUID.fromString("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4")));

        assertThat(exception.getMessage(), is("Customer not found"));
    }
}
