package com.hagag.shopsphere_ecommerce.dto.orderitem;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDto {

    private Long id;

    private Long productId;

    private Integer quantity;

    private BigDecimal price;

}
