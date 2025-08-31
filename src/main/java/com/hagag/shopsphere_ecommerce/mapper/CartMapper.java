package com.hagag.shopsphere_ecommerce.mapper;

import com.hagag.shopsphere_ecommerce.dto.cart.CartResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "user.id", target = "userId")
    CartResponseDto toDto(Cart cart);

}
