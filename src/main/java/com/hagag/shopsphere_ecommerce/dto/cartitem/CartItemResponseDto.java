package com.hagag.shopsphere_ecommerce.dto.cartitem;

import lombok.Data;

@Data
public class CartItemResponseDto {

    private Long id;

    private Long productId;

    private Integer quantity;

    private Long cartId;

}
