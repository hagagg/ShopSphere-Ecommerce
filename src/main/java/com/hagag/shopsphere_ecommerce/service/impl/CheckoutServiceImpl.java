package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.order.OrderResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Address;
import com.hagag.shopsphere_ecommerce.entity.Cart;
import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.enums.CartStatus;
import com.hagag.shopsphere_ecommerce.enums.UserRole;
import com.hagag.shopsphere_ecommerce.exception.custom.BusinessException;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.exception.custom.UnauthorizedActionException;
import com.hagag.shopsphere_ecommerce.mapper.OrderMapper;
import com.hagag.shopsphere_ecommerce.repository.CartRepo;
import com.hagag.shopsphere_ecommerce.repository.OrderRepo;
import com.hagag.shopsphere_ecommerce.service.CheckoutService;
import com.hagag.shopsphere_ecommerce.service.OrderService;
import com.hagag.shopsphere_ecommerce.service.ShippingService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final SecurityUtil securityUtil;
    private final CartRepo cartRepo;
    private final ShippingService shippingService;
    private final OrderService orderService;
    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDto checkout(Long shippingAddressId) {
        User currentUser = securityUtil.getCurrentUser();

        if (currentUser.getRole() != UserRole.CUSTOMER) {
            throw new UnauthorizedActionException("Only customers can create orders");
        }

        Cart cart = cartRepo.findByUserAndStatus(currentUser, CartStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("No active cart found"));

        if (cart.getCartItems().isEmpty()) {
            throw new BusinessException("Cannot checkout with an empty cart", HttpStatus.BAD_REQUEST);
        }

        Address shippingAddress = shippingService.resolveShippingAddress(shippingAddressId);

        Order order = orderService.createOrderFromCart(cart, shippingAddress);
        orderRepo.save(order);

        cart.setStatus(CartStatus.ORDERED);
        cartRepo.save(cart);

        return orderMapper.toDto(order);
    }

}
