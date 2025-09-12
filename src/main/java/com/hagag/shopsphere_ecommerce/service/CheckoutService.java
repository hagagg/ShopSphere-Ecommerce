package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.order.OrderResponseDto;

public interface CheckoutService {

    OrderResponseDto checkout(Long shippingAddressId);
}
