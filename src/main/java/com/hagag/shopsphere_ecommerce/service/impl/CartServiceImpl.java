package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.cart.CartResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Cart;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.enums.CartStatus;
import com.hagag.shopsphere_ecommerce.mapper.CartMapper;
import com.hagag.shopsphere_ecommerce.repository.CartRepo;
import com.hagag.shopsphere_ecommerce.service.CartService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final CartMapper cartMapper;
    private final SecurityUtil securityUtil;

    @Override
    public Cart getOrCreateActiveCart() {
        User currentUser = securityUtil.getCurrentUser();

        return cartRepo.findByUserAndStatus(currentUser, CartStatus.ACTIVE)
                .orElseGet(() -> {
                    Cart cart = Cart.builder()
                            .user(currentUser)
                            .status(CartStatus.ACTIVE)
                            .build();
                    return cartRepo.save(cart);
                });
    }


}
