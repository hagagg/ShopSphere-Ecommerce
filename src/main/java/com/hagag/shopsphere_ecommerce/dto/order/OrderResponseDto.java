package com.hagag.shopsphere_ecommerce.dto.order;

import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponseDto {

    private Long id;

    private Long userId;

    private Long shippingAddressId;

    private BigDecimal totalAmount;

    private OrderStatus orderStatus;

}
