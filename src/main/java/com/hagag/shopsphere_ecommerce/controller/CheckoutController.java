package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.order.OrderResponseDto;
import com.hagag.shopsphere_ecommerce.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public OrderResponseDto checkout(@RequestParam(required = false) Long shippingAddressId) {

        return checkoutService.checkout(shippingAddressId);
    }

}
