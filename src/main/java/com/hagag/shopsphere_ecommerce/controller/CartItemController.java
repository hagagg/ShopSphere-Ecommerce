package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.cartitem.CartItemRequestDto;
import com.hagag.shopsphere_ecommerce.dto.cartitem.CartItemResponseDto;
import com.hagag.shopsphere_ecommerce.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public CartItemResponseDto addCartItem(@Valid @RequestBody CartItemRequestDto cartItemRequestDto) {
        return cartItemService.addCartItem(cartItemRequestDto);
    }

    @GetMapping
    public List<CartItemResponseDto> getCartItems() {
        return cartItemService.getCartItems();
    }

    @PutMapping("/{cartItemId}")
    public CartItemResponseDto updateCartItemQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) {
        return cartItemService.updateCartItemQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
    }

}
