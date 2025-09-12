package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartItemService cartItemService;

}
