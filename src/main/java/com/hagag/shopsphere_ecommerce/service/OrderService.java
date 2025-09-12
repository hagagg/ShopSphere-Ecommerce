package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.order.OrderResponseDto;
import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Address;
import com.hagag.shopsphere_ecommerce.entity.Cart;
import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Order createOrderFromCart(Cart cart, Address shippingAddress);

    OrderResponseDto getOrderById(Long orderId);

    PaginatedResponseDto<OrderResponseDto> getCurrentUserOrders(Pageable pageable);

    PaginatedResponseDto<OrderResponseDto> getOrdersByUserId(Long userId, Pageable pageable);

    PaginatedResponseDto<OrderResponseDto> getAllOrders(Pageable pageable);

    OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status);

    OrderResponseDto cancelOrder(Long orderId);
}
