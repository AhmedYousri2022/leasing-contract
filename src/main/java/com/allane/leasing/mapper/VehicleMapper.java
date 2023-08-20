package com.allane.leasing.mapper;

import com.allane.leasing.dto.vehicle.VehicleDetailsRequestDto;
import com.allane.leasing.dto.vehicle.VehicleDetailsResponseDto;
import com.allane.leasing.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VehicleMapper {

    @Mapping(target = "vehicleIdentificationNumber", source = "vehicleIdentificationNumber", qualifiedByName = "getVin")
    VehicleDetailsResponseDto toResponseDto(Vehicle vehicle);

    @Named("getVin")
    default String getVin(String vin) {
        if (vin == null || vin.isEmpty()) {
            return "-";
        }
        return vin;
    }

    Vehicle toVehicle(VehicleDetailsRequestDto dto);

    void updateVehicleFromDto(VehicleDetailsRequestDto dto, @MappingTarget Vehicle vehicle);
}
