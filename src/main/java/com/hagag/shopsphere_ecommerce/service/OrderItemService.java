package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemRequestDto;
import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderItemService {

    OrderItemResponseDto createOrderItem(Long orderId, @Valid OrderItemRequestDto orderItemRequestDto);

    OrderItemResponseDto updateOrderItemQuantity(Long orderItemId, int newQuantity);

    void deleteOrderItem(Long orderItemId);

    List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId);

    List<OrderItemResponseDto> getOrderItemsForCurrentUser();

    List<OrderItemResponseDto> getAllOrderItems();
}
