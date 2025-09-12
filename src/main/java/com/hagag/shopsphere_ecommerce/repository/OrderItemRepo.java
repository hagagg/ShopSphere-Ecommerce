package com.hagag.shopsphere_ecommerce.repository;

import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.entity.OrderItem;
import com.hagag.shopsphere_ecommerce.entity.Product;
import com.hagag.shopsphere_ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

    boolean existsByOrderAndProduct(Order order, Product product);

    List<OrderItem> findAllByOrder_User(User currentUser);
}
