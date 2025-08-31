package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.entity.Cart;

public interface CartService {

    Cart getOrCreateActiveCart();
}
