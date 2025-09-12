package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemResponseDto;
import java.util.List;

public interface OrderItemService {


    List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId);

    List<OrderItemResponseDto> getOrderItemsForCurrentUser();

    List<OrderItemResponseDto> getAllOrderItems();
}
