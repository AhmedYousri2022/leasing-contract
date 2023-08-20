package com.allane.leasing.mapper;

import com.allane.leasing.dto.customer.CustomerDetailsRequestDto;
import com.allane.leasing.dto.customer.CustomerDetailsResponseDto;
import com.allane.leasing.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerDetailsResponseDto toDto(Customer customer);
    Customer toCustomer(CustomerDetailsRequestDto dto);

    void updateCustomerFromDto(CustomerDetailsRequestDto dto, @MappingTarget Customer customer);

}
