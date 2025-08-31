package com.hagag.shopsphere_ecommerce.dto.cart;

import com.hagag.shopsphere_ecommerce.enums.CartStatus;
import lombok.Data;

import java.util.List;

@Data
public class CartResponseDto {

        private Long id;

        private Long userId;

        private List<CartResponseDto> cartItems;

        private CartStatus status;
}
