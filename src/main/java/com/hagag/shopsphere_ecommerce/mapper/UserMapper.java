package com.hagag.shopsphere_ecommerce.mapper;

import com.hagag.shopsphere_ecommerce.dto.user.UserResponseDto;
import com.hagag.shopsphere_ecommerce.dto.user.UserSummaryDto;
import com.hagag.shopsphere_ecommerce.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    UserSummaryDto toSummaryDto(User user);
}
