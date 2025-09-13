package com.hagag.shopsphere_ecommerce.repository;

import com.hagag.shopsphere_ecommerce.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

}
