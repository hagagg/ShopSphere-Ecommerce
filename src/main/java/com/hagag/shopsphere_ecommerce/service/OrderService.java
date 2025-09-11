package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.order.OrderRequestDto;
import com.hagag.shopsphere_ecommerce.dto.order.OrderResponseDto;
import jakarta.validation.Valid;

public interface OrderService {

    OrderResponseDto createOrder(@Valid OrderRequestDto orderRequestDto);
}
