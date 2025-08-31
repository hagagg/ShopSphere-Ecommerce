package com.hagag.shopsphere_ecommerce.repository;

import com.hagag.shopsphere_ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart_User_Id(Long cartUserId);
}
