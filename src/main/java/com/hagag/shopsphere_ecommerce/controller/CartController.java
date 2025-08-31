package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.cart.CartResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Cart;
import com.hagag.shopsphere_ecommerce.mapper.CartMapper;
import com.hagag.shopsphere_ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping("/active")
    public CartResponseDto getActiveCart() {
        Cart cart = cartService.getOrCreateActiveCart();
        return cartMapper.toDto(cart);
    }



}
