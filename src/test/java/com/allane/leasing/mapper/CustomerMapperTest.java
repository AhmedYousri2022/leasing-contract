package com.allane.leasing.mapper;

import com.allane.leasing.dto.customer.CustomerDetailsRequestDto;
import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.model.Customer;
import com.allane.leasing.stub.customer.CustomerDetailsRequestDtoStub;
import com.allane.leasing.stub.customer.CustomerModelStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CustomerMapperTest {

    private final CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @Test
    void toDto() {
        Customer customer = CustomerModelStub.getModel();

        CustomerDetailsResponseDto dto = customerMapper.toDto(customer);

        assertThat(dto.getFirstName(), is(customer.getFirstName()));
        assertThat(dto.getLastName(), is(customer.getLastName()));
        assertThat(dto.getBirthdate(), is(customer.getBirthdate()));
    }

    @Test
    void toCustomer() {
        CustomerDetailsRequestDto requestDto = CustomerDetailsRequestDtoStub.getDto();

        Customer customer = customerMapper.toCustomer(requestDto);

        assertThat(customer.getFirstName(), is(requestDto.getFirstName()));
        assertThat(customer.getLastName(), is(requestDto.getLastName()));
        assertThat(customer.getBirthdate(), is(requestDto.getBirthdate()));
    }
}
