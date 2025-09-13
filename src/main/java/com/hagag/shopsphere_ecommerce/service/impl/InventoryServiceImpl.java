package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.entity.Product;
import com.hagag.shopsphere_ecommerce.exception.custom.BusinessException;
import com.hagag.shopsphere_ecommerce.repository.ProductRepo;
import com.hagag.shopsphere_ecommerce.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepo productRepo;

    @Override
    @Transactional
    public void validateAndDeductStock(Product product, int quantity) {
        if (product.getStockQuantity() < quantity) {

            throw new BusinessException("Insufficient stock for product: " + product.getName(), HttpStatus.BAD_REQUEST);
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepo.save(product);
    }

}
