package com.hagag.shopsphere_ecommerce.strategy;

import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.enums.PaymentStatus;

import java.math.BigDecimal;

public interface PaymentStrategy {

    PaymentStatus pay(Order order, BigDecimal amount);

}
