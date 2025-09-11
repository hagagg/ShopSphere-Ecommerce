package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.order.OrderRequestDto;
import com.hagag.shopsphere_ecommerce.dto.order.OrderResponseDto;
import com.hagag.shopsphere_ecommerce.entity.*;
import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import com.hagag.shopsphere_ecommerce.enums.UserRole;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.exception.custom.UnauthorizedActionException;
import com.hagag.shopsphere_ecommerce.mapper.OrderMapper;
import com.hagag.shopsphere_ecommerce.repository.AddressRepo;
import com.hagag.shopsphere_ecommerce.repository.OrderRepo;
import com.hagag.shopsphere_ecommerce.repository.ProductRepo;
import com.hagag.shopsphere_ecommerce.service.OrderService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
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


}
