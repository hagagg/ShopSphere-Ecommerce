package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.cart.CartResponseDto;
import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Cart;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartService {

    Cart getOrCreateActiveCart();

    CartResponseDto createNewCart();

    CartResponseDto getCartById(Long cartId);

    List<CartResponseDto> getAllCartsForCurrentUser();

    void deleteCart(Long cartId);

    PaginatedResponseDto<CartResponseDto> getAllCarts(Pageable pageable);
}
