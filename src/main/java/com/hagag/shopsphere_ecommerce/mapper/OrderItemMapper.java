package com.hagag.shopsphere_ecommerce.mapper;

import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemRequestDto;
import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemResponseDto;
import com.hagag.shopsphere_ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OrderItem toEntity (OrderItemRequestDto OrderItemRequestDto);

    @Mapping(source = "product.id", target = "productId")
    OrderItemResponseDto toDto (OrderItem orderItem);
}
