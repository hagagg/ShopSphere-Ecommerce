package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemRequestDto;
import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.entity.OrderItem;
import com.hagag.shopsphere_ecommerce.entity.Product;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.enums.UserRole;
import com.hagag.shopsphere_ecommerce.exception.custom.BusinessException;
import com.hagag.shopsphere_ecommerce.exception.custom.DuplicateResourceException;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.exception.custom.UnauthorizedActionException;
import com.hagag.shopsphere_ecommerce.mapper.OrderItemMapper;
import com.hagag.shopsphere_ecommerce.repository.OrderItemRepo;
import com.hagag.shopsphere_ecommerce.repository.OrderRepo;
import com.hagag.shopsphere_ecommerce.repository.ProductRepo;
import com.hagag.shopsphere_ecommerce.service.OrderItemService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepo orderItemRepo;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final OrderItemMapper orderItemMapper;
    private final SecurityUtil securityUtil;
    private final AccessGuard accessGuard;

    @Override
    public OrderItemResponseDto createOrderItem(Long orderId, OrderItemRequestDto orderItemRequestDto) {
        User currentUser = securityUtil.getCurrentUser();

        if (currentUser.getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedActionException("You are not allowed to perform this action");
        }

        Order order = orderRepo.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order Not Found"));

        Product product = productRepo.findById(orderItemRequestDto.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

        boolean exists = orderItemRepo.existsByOrderAndProduct(order, product);
        if (exists) {
            throw new DuplicateResourceException("Product already exists in the order. Please update it instead");
        }

        OrderItem orderItem = orderItemMapper.toEntity(orderItemRequestDto);

        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequestDto.getQuantity())));

        OrderItem savedOrderItem = orderItemRepo.save(orderItem);

        return orderItemMapper.toDto(savedOrderItem);
        }

    @Override
    @Transactional
    public OrderItemResponseDto updateOrderItemQuantity(Long orderItemId, int newQuantity) {
        OrderItem orderItem = orderItemRepo.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem Not Found"));

        accessGuard.checkUserOnly(orderItem.getOrder().getUser());

        if (newQuantity < 1) {
            throw new BusinessException("Quantity must be at least 1" , HttpStatus.BAD_REQUEST);
        }

        orderItem.setQuantity(newQuantity);
        orderItem.setPrice(orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity)));

        OrderItem savedOrderItem = orderItemRepo.save(orderItem);
        return orderItemMapper.toDto(savedOrderItem);
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepo.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem Not Found"));

        accessGuard.checkUserOnly(orderItem.getOrder().getUser());

        orderItemRepo.delete(orderItem);
    }

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
