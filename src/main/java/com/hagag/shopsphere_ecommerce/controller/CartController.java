package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.cart.CartResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Cart;
import com.hagag.shopsphere_ecommerce.mapper.CartMapper;
import com.hagag.shopsphere_ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/new")
    public CartResponseDto createNewCart() {

        return cartService.createNewCart();
    }

    @GetMapping("/{cartId}")
    public CartResponseDto getCartById(@PathVariable Long cartId) {

        return cartService.getCartById(cartId);
    }

    @GetMapping("/my-carts")
    public List<CartResponseDto> getAllCartsForCurrentUser() {

        return cartService.getAllCartsForCurrentUser();
    }

    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable Long cartId) {

        cartService.deleteCart(cartId);
    }

    @GetMapping("/all")
    public List<CartResponseDto> getAllCarts() {

        return cartService.getAllCarts();
    }


}
