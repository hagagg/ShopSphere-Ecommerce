package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.order.OrderRequestDto;
import com.hagag.shopsphere_ecommerce.dto.order.OrderResponseDto;
import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.entity.*;
import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import com.hagag.shopsphere_ecommerce.enums.UserRole;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.exception.custom.UnauthorizedActionException;
import com.hagag.shopsphere_ecommerce.mapper.OrderMapper;
import com.hagag.shopsphere_ecommerce.mapper.PaginationMapper;
import com.hagag.shopsphere_ecommerce.repository.AddressRepo;
import com.hagag.shopsphere_ecommerce.repository.OrderRepo;
import com.hagag.shopsphere_ecommerce.repository.ProductRepo;
import com.hagag.shopsphere_ecommerce.repository.UserRepo;
import com.hagag.shopsphere_ecommerce.service.OrderService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final AddressRepo addressRepo;
    private final OrderPricingServiceImpl orderPricingService;
    private final ProductRepo productRepo;
    private final AccessGuard accessGuard;
    private final PaginationMapper paginationMapper;
    private final UserRepo userRepo;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        User currentUser = securityUtil.getCurrentUser();

        if (currentUser.getRole() != UserRole.CUSTOMER) {
            throw new UnauthorizedActionException("Only customers can create orders");
        }

        Address shippingAddress;
        if (orderRequestDto.getShippingAddressId() != null) {
            shippingAddress = addressRepo.findById(orderRequestDto.getShippingAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

            if (!shippingAddress.getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedActionException("You are not allowed to use this address");
            }
        } else {
            shippingAddress = currentUser.getAddresses()
                    .stream()
                    .filter(Address::isDefaultAddress)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("No default address found for this user"));
        }

        Order order = orderMapper.toEntity (orderRequestDto);
        order.setUser (currentUser);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setShippingAddress(shippingAddress);

        List<OrderItem> items = orderRequestDto.getOrderItems()
                .stream()
                .map(orderItemRequestDto -> {
                    Product product = productRepo.findById(orderItemRequestDto.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                    return OrderItem.builder()
                            .product(product)
                            .quantity(orderItemRequestDto.getQuantity())
                            .order(order)
                            .build();
                })
                .collect(Collectors.toList());

        order.setOrderItems(items);

        BigDecimal totalAmount = orderPricingService.calculateTotal(order);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepo.save (order);
        return orderMapper.toDto (savedOrder);
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
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        accessGuard.checkAdminOnly();

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
