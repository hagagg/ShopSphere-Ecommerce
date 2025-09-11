package com.hagag.shopsphere_ecommerce.dto.order;

import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemResponseDto;
import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderResponseDto {

    private Long id;

    private Long userId;

    private Long shippingAddressId;

    private BigDecimal totalAmount;

    private OrderStatus orderStatus;

    private List<OrderItemResponseDto> orderItems;

}
