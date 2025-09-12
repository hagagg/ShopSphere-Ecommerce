package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.entity.OrderItem;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.mapper.OrderItemMapper;
import com.hagag.shopsphere_ecommerce.repository.OrderItemRepo;
import com.hagag.shopsphere_ecommerce.repository.OrderRepo;
import com.hagag.shopsphere_ecommerce.service.OrderItemService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepo orderItemRepo;
    private final OrderRepo orderRepo;
    private final OrderItemMapper orderItemMapper;
    private final SecurityUtil securityUtil;
    private final AccessGuard accessGuard;

    @Override
    @Transactional
    public List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found"));

        accessGuard.checkUserOrAdmin(order.getUser());

        return order.getOrderItems()
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> getOrderItemsForCurrentUser() {
        User currentUser = securityUtil.getCurrentUser();

        List<OrderItem> items = orderItemRepo.findAllByOrder_User(currentUser);

        return items.stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> getAllOrderItems() {
        accessGuard.checkAdminOnly();

        return orderItemRepo.findAll()
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }


}
