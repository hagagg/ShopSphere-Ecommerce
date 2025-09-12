package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.order.OrderResponseDto;
import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Address;
import com.hagag.shopsphere_ecommerce.entity.Cart;
import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import com.hagag.shopsphere_ecommerce.exception.custom.BusinessException;
import com.hagag.shopsphere_ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrderFromCart (Cart cart, Address shippingAddress) {

        return orderService.createOrderFromCart (cart, shippingAddress);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrderById(@PathVariable Long orderId) {

        return orderService.getOrderById(orderId);
    }

    @GetMapping("/me")
    public PaginatedResponseDto<OrderResponseDto> getCurrentUserOrders(Pageable pageable) {

        return orderService.getCurrentUserOrders(pageable);
    }

    @GetMapping("/user/{userId}")
    public PaginatedResponseDto<OrderResponseDto> getOrdersByUserId(@PathVariable Long userId, Pageable pageable) {

        return orderService.getOrdersByUserId(userId, pageable);
    }

    @GetMapping
    public PaginatedResponseDto<OrderResponseDto> getAllOrders(Pageable pageable) {

        return orderService.getAllOrders(pageable);
    }

    @PatchMapping("/{orderId}/status")
    public OrderResponseDto updateOrderStatus( @PathVariable Long orderId, @RequestParam String status) {

        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid Order Status: " + status, HttpStatus.BAD_REQUEST);
        }
        return orderService.updateOrderStatus(orderId, orderStatus);
    }

    @PatchMapping("/cancel/{orderId}")
    public OrderResponseDto cancelOrder(@PathVariable Long orderId) {

        return orderService.cancelOrder(orderId);
    }

}
