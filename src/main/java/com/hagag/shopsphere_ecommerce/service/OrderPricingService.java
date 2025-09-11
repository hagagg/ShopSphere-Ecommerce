package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.entity.Order;

import java.math.BigDecimal;

public interface OrderPricingService {

    BigDecimal calculateTotal(Order order);
}
