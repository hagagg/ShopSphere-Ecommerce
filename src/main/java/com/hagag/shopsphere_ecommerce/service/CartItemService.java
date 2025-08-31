package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.cartitem.CartItemRequestDto;
import com.hagag.shopsphere_ecommerce.dto.cartitem.CartItemResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CartItemService {

    CartItemResponseDto addCartItem(@Valid CartItemRequestDto cartItemRequestDto);

    List<CartItemResponseDto> getCartItems();

    CartItemResponseDto updateCartItemQuantity(Long cartItemId, int quantity);

    void deleteCartItem(Long cartItemId);

}
