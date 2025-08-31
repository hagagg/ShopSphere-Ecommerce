package com.hagag.shopsphere_ecommerce.dto.cart;

import com.hagag.shopsphere_ecommerce.enums.CartStatus;
import lombok.Data;

@Data
public class CartResponseDto {

        private Long id;

        private Long userId;

        private CartStatus status;
}
