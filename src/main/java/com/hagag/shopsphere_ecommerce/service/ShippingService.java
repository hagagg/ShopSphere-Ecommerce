package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.entity.Address;

public interface ShippingService {

    Address resolveShippingAddress(Long addressId);
}
