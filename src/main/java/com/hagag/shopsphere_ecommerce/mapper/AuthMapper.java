package com.hagag.shopsphere_ecommerce.mapper;

import com.hagag.shopsphere_ecommerce.dto.authentication.RegisterRequestDto;
import com.hagag.shopsphere_ecommerce.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt" , ignore = true)
//    @Mapping(target = "updatedAt" , ignore = true)
//    @Mapping(target = "username", source = "username")
    User toEntity (RegisterRequestDto registerRequestDto);
}
