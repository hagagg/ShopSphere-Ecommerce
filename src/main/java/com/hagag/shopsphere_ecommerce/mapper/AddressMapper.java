package com.hagag.shopsphere_ecommerce.mapper;

import com.hagag.shopsphere_ecommerce.dto.address.AddressRequestDto;
import com.hagag.shopsphere_ecommerce.dto.address.AddressResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Address;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Address toEntity(AddressRequestDto addressRequestDto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "defaultAddress", source = "defaultAddress")
    AddressResponseDto toDto(Address address);
}
