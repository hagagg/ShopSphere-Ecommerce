package com.hagag.shopsphere_ecommerce.repository;

import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.entity.Shipment;
import com.hagag.shopsphere_ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepo extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByOrder(Order order);

    Page<Shipment> findByOrder_User(User user, Pageable pageable);
}
