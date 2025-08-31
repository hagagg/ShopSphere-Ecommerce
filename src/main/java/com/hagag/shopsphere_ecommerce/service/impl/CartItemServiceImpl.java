package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.cartitem.CartItemRequestDto;
import com.hagag.shopsphere_ecommerce.dto.cartitem.CartItemResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Cart;
import com.hagag.shopsphere_ecommerce.entity.CartItem;
import com.hagag.shopsphere_ecommerce.entity.Product;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.exception.custom.DuplicateResourceException;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.mapper.CartItemMapper;
import com.hagag.shopsphere_ecommerce.repository.CartItemRepo;
import com.hagag.shopsphere_ecommerce.repository.ProductRepo;
import com.hagag.shopsphere_ecommerce.service.CartItemService;
import com.hagag.shopsphere_ecommerce.service.CartService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepo cartItemRepo;
    private final CartItemMapper cartItemMapper;
    private final ProductRepo productRepo;
    private final SecurityUtil securityUtil;
    private final AccessGuard accessGuard;
    private final CartService cartService;

    @Override
    public CartItemResponseDto addCartItem(CartItemRequestDto cartItemRequestDto) {
        Product product = productRepo.findById(cartItemRequestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + cartItemRequestDto.getProductId() + " not found"));

        CartItem cartItem = cartItemMapper.toEntity(cartItemRequestDto);
        cartItem.setProduct(product);

        Cart cart = cartService.getOrCreateActiveCart();
        cartItem.setCart(cart);

        try {
            CartItem savedCartItem = cartItemRepo.save(cartItem);
            return cartItemMapper.toDto(savedCartItem);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Product with id " + cartItemRequestDto.getProductId() + " already exists in the cart");
        }
    }

    @Override
    public List<CartItemResponseDto> getCartItems() {
        User currentUser = securityUtil.getCurrentUser();

        List<CartItem> cartItems = cartItemRepo.findByCart_User_Id(currentUser.getId());

        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty for user id " + currentUser.getId());
        }

        return cartItems.stream().map(cartItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartItemResponseDto updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem with id " + cartItemId + " not found"));

        User ownerUser = cartItem.getCart().getUser();
        accessGuard.checkUserOnly(ownerUser);

        cartItem.setQuantity(quantity);
        CartItem savedCartItem = cartItemRepo.save(cartItem);

        return cartItemMapper.toDto(savedCartItem);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem with id " + cartItemId + " not found"));

        User ownerUser = cartItem.getCart().getUser();
        accessGuard.checkUserOnly(ownerUser);

        cartItemRepo.delete(cartItem);
    }


}
