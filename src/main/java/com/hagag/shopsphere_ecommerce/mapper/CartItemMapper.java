package com.hagag.shopsphere_ecommerce.mapper;

import com.hagag.shopsphere_ecommerce.dto.cartitem.CartItemRequestDto;
import com.hagag.shopsphere_ecommerce.dto.cartitem.CartItemResponseDto;
import com.hagag.shopsphere_ecommerce.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CartItem toEntity(CartItemRequestDto cartItemRequestDto);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "cartId", source = "cart.id")
    CartItemResponseDto toDto(CartItem cartItem);
}
