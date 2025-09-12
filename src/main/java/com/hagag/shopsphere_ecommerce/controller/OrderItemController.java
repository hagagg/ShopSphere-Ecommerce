package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemResponseDto;
import com.hagag.shopsphere_ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/order/{orderId}")
    public List<OrderItemResponseDto> getOrderItemsByOrderId(@PathVariable Long orderId) {

        return orderItemService.getOrderItemsByOrderId(orderId);
    }

    @GetMapping("/me")
    public List<OrderItemResponseDto> getMyOrderItems() {

        return orderItemService.getOrderItemsForCurrentUser();
    }

    @GetMapping
    public List<OrderItemResponseDto> getAllOrderItems() {

        return orderItemService.getAllOrderItems();
    }



}
