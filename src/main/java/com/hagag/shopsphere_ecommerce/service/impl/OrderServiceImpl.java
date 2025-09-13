package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.order.OrderResponseDto;
import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.entity.*;
import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import com.hagag.shopsphere_ecommerce.enums.UserRole;
import com.hagag.shopsphere_ecommerce.exception.custom.BusinessException;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.exception.custom.UnauthorizedActionException;
import com.hagag.shopsphere_ecommerce.mapper.OrderMapper;
import com.hagag.shopsphere_ecommerce.mapper.PaginationMapper;
import com.hagag.shopsphere_ecommerce.repository.*;
import com.hagag.shopsphere_ecommerce.service.InventoryService;
import com.hagag.shopsphere_ecommerce.service.OrderService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import com.hagag.shopsphere_ecommerce.validation.OrderTransitionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;
    private final SecurityUtil securityUtil;
    private final OrderPricingServiceImpl orderPricingService;
    private final InventoryService inventoryService;
    private final AccessGuard accessGuard;
    private final PaginationMapper paginationMapper;
    private final UserRepo userRepo;
    private final OrderTransitionValidator orderTransitionValidator;

    @Override
    @Transactional
    public Order createOrderFromCart(Cart cart, Address shippingAddress) {
        Order order = Order.builder()
                .user(cart.getUser())
                .orderStatus(OrderStatus.PENDING)
                .shippingAddress(shippingAddress)
                .build();

        List<OrderItem> items = cart.getCartItems()
                .stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();

                    inventoryService.validateAndDeductStock(product, cartItem.getQuantity());

                    return OrderItem.builder()
                            .product(cartItem.getProduct())
                            .quantity(cartItem.getQuantity())
                            .price(product.getPrice())
                            .order(order)
                            .build();
                }).collect(Collectors.toList());

        order.setOrderItems(items);

        BigDecimal totalAmount = orderPricingService.calculateTotal(order);
        order.setTotalAmount(totalAmount);

        return orderRepo.save (order);
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        accessGuard.checkUserOrAdmin(order.getUser());

        return orderMapper.toDto(order);
    }

    @Override
    public PaginatedResponseDto<OrderResponseDto> getCurrentUserOrders(Pageable pageable) {
        User currentUser = securityUtil.getCurrentUser();

        Page<OrderResponseDto> ordersPage = orderRepo.findByUserId(currentUser.getId(), pageable)
                .map(orderMapper::toDto);

        return paginationMapper.toPaginatedResponse(ordersPage);
    }

    @Override
    public PaginatedResponseDto<OrderResponseDto> getOrdersByUserId(Long userId, Pageable pageable) {
        accessGuard.checkAdminOnly();

        userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Page<OrderResponseDto> ordersPage = orderRepo.findByUserId(userId, pageable)
                .map(orderMapper::toDto);

        return paginationMapper.toPaginatedResponse(ordersPage);
    }

    @Override
    public PaginatedResponseDto<OrderResponseDto> getAllOrders(Pageable pageable) {
        accessGuard.checkAdminOnly();

        Page<OrderResponseDto> ordersPage = orderRepo.findAll(pageable)
                .map(orderMapper::toDto);

        return paginationMapper.toPaginatedResponse(ordersPage);
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        accessGuard.checkAdminOnly();

        if (!orderTransitionValidator.canTransition(order.getOrderStatus(), status)) {

            throw new BusinessException("Invalid transition from " + order.getOrderStatus() + " to " + status , HttpStatus.BAD_REQUEST);
        }

        if (status == OrderStatus.PAID || status == OrderStatus.SHIPPED) {

            throw new BusinessException("Order status " + status + " can only be set automatically via payment", HttpStatus.BAD_REQUEST);
        }

        if (status == OrderStatus.CANCELLED) {
            // Prevent cancellation after shipped or delivered
            if (order.getOrderStatus() == OrderStatus.SHIPPED) {

                throw new BusinessException("Cannot cancel an order that has already been shipped or delivered", HttpStatus.BAD_REQUEST);
            }
        }

        order.setOrderStatus(status);
        orderRepo.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    public OrderResponseDto cancelOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        User currentUser = securityUtil.getCurrentUser();

        accessGuard.checkUserOrAdmin(order.getUser());

        if (!currentUser.getRole().equals(UserRole.ADMIN) && order.getOrderStatus() != OrderStatus.PENDING) {
            throw new UnauthorizedActionException("You can only cancel pending orders");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepo.save(order);
        return orderMapper.toDto(order);
    }


}
