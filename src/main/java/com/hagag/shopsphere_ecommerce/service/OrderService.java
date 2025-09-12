package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.order.OrderRequestDto;
import com.hagag.shopsphere_ecommerce.dto.order.OrderResponseDto;
import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponseDto createOrder(@Valid OrderRequestDto orderRequestDto);

    OrderResponseDto getOrderById(Long orderId);

    PaginatedResponseDto<OrderResponseDto> getCurrentUserOrders(Pageable pageable);

    PaginatedResponseDto<OrderResponseDto> getOrdersByUserId(Long userId, Pageable pageable);

    PaginatedResponseDto<OrderResponseDto> getAllOrders(Pageable pageable);

    OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status);

    OrderResponseDto cancelOrder(Long orderId);
}
