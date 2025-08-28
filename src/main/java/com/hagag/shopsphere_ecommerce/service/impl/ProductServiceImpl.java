package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.product.ProductRequestDto;
import com.hagag.shopsphere_ecommerce.dto.product.ProductResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Category;
import com.hagag.shopsphere_ecommerce.entity.Product;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.mapper.ProductMapper;
import com.hagag.shopsphere_ecommerce.repository.CategoryRepo;
import com.hagag.shopsphere_ecommerce.repository.ProductRepo;
import com.hagag.shopsphere_ecommerce.service.ProductService;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final AccessGuard accessGuard;
    private final CategoryRepo categoryRepo;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        accessGuard.checkAdminOnly();

        Category category = categoryRepo.findById(productRequestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + productRequestDto.getCategoryId() + " not found"));

        Product product = productMapper.toEntity(productRequestDto);
        product.setCategory(category);

        Product savedProduct = productRepo.save(product);

        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found"));

        return productMapper.toDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepo.findAll ()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto) {
        accessGuard.checkAdminOnly();

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found"));

        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setStockQuantity(productRequestDto.getStockQuantity());

        Category category = categoryRepo.findById(productRequestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + productRequestDto.getCategoryId() + " not found"));
        product.setCategory(category);

        Product updatedProduct = productRepo.save(product);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        accessGuard.checkAdminOnly();

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found"));

        productRepo.delete(product);
    }


}
