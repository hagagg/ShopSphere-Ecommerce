package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemRequestDto;
import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemResponseDto;
import com.hagag.shopsphere_ecommerce.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping("/order/{orderId}")
    public OrderItemResponseDto addOrderItem(@PathVariable Long orderId, @Valid @RequestBody OrderItemRequestDto orderItemRequestDto) {

        return orderItemService.createOrderItem(orderId, orderItemRequestDto);
    }

    @PatchMapping("/{orderItemId}/quantity/{newQuantity}")
    public OrderItemResponseDto updateOrderItemQuantity( @PathVariable Long orderItemId, @PathVariable int newQuantity) {

        return orderItemService.updateOrderItemQuantity(orderItemId, newQuantity);
    }

    @DeleteMapping("/{orderItemId}")
    public void deleteOrderItem(@PathVariable Long orderItemId) {

        orderItemService.deleteOrderItem(orderItemId);
    }

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
