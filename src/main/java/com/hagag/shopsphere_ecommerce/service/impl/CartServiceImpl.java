package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.cart.CartResponseDto;
import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Cart;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.enums.CartStatus;
import com.hagag.shopsphere_ecommerce.enums.UserRole;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.exception.custom.UnauthorizedActionException;
import com.hagag.shopsphere_ecommerce.mapper.CartMapper;
import com.hagag.shopsphere_ecommerce.mapper.PaginationMapper;
import com.hagag.shopsphere_ecommerce.repository.CartRepo;
import com.hagag.shopsphere_ecommerce.service.CartService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final CartMapper cartMapper;
    private final SecurityUtil securityUtil;
    private final AccessGuard accessGuard;
    private final PaginationMapper paginationMapper;

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

    @Override
    @Transactional
    public CartResponseDto createNewCart() {
        User currentUser = securityUtil.getCurrentUser();

        Cart existitingActiveCart = cartRepo.findByUserAndStatus(currentUser, CartStatus.ACTIVE).orElse(null);

        Cart cart;
        if (existitingActiveCart == null) {
            cart = Cart.builder()
                    .user(currentUser)
                    .status(CartStatus.ACTIVE)
                    .build();
        } else {
            cart = Cart.builder()
                    .user(currentUser)
                    .status(CartStatus.PENDING)
                    .build();

        }
        Cart savedCart = cartRepo.save(cart);
        return cartMapper.toDto(savedCart);
    }

    @Override
    @Transactional
    public CartResponseDto getCartById(Long cartId) {
        accessGuard.checkAdminOnly();

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found"));

        return cartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public List<CartResponseDto> getAllCartsForCurrentUser() {
        User currentUser = securityUtil.getCurrentUser();

        if (currentUser.getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedActionException("Admins are not allowed to access this method");
        }

        return cartRepo.findAllByUser(currentUser)
                .stream()
                .map(cartMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCart(Long cartId) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found"));

        accessGuard.checkUserOrAdmin(cart.getUser());

        cartRepo.delete(cart);
    }

    @Override
    @Transactional
    public PaginatedResponseDto<CartResponseDto> getAllCarts(Pageable pageable) {
        accessGuard.checkAdminOnly();

        Page<Cart> carts = cartRepo.findAll(pageable);
        Page<CartResponseDto> dtoPage = carts.map(cartMapper::toDto);

        return paginationMapper.toPaginatedResponse(dtoPage);
    }


}
