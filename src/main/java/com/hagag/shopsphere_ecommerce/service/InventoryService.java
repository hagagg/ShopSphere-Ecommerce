package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.entity.Product;

public interface InventoryService {

    void validateAndDeductStock(Product product, int quantity);

}
