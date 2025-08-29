package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.dto.product.ProductRequestDto;
import com.hagag.shopsphere_ecommerce.dto.product.ProductResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Category;
import com.hagag.shopsphere_ecommerce.entity.Product;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.mapper.PaginationMapper;
import com.hagag.shopsphere_ecommerce.mapper.ProductMapper;
import com.hagag.shopsphere_ecommerce.repository.CategoryRepo;
import com.hagag.shopsphere_ecommerce.repository.ProductRepo;
import com.hagag.shopsphere_ecommerce.service.ProductService;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final AccessGuard accessGuard;
    private final CategoryRepo categoryRepo;
    private final PaginationMapper paginationMapper;

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
    public PaginatedResponseDto<ProductResponseDto> getAllProducts(Pageable pageable) {

        Page<Product> productPage = productRepo.findAll(pageable);

        Page<ProductResponseDto> dtoPage = productPage.map(productMapper::toDto);

        return paginationMapper.toPaginatedResponse(dtoPage);
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

    @Override
    public PaginatedResponseDto<ProductResponseDto> searchProductsByName(String name, Pageable pageable) {

        Page<Product> productPage = productRepo.findByNameContainingIgnoreCase(name, pageable);

        Page<ProductResponseDto> dtoPage = productPage.map(productMapper::toDto);

        return paginationMapper.toPaginatedResponse(dtoPage);
    }

    @Override
    public PaginatedResponseDto<ProductResponseDto> getProductsByCategoryId(Long categoryId, Pageable pageable) {

        Page<Product> productPage = productRepo.findByCategory_Id(categoryId, pageable);

        Page<ProductResponseDto> dtoPage = productPage.map(productMapper::toDto);

        return paginationMapper.toPaginatedResponse(dtoPage);
    }

    @Override
    public PaginatedResponseDto<ProductResponseDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {

        Page<Product> productPage = productRepo.findByPriceBetween(minPrice, maxPrice, pageable);

        Page<ProductResponseDto> dtoPage = productPage.map(productMapper::toDto);

        return paginationMapper.toPaginatedResponse(dtoPage);
    }

    @Override
    public PaginatedResponseDto<ProductResponseDto> getProductsSortedBy(String field, String direction, Pageable pageable) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(Sort.Direction.DESC, field)
                : Sort.by(Sort.Direction.ASC, field);

        Page<Product> productPage = productRepo.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));

        Page<ProductResponseDto> dtoPage = productPage.map(productMapper::toDto);

        return paginationMapper.toPaginatedResponse(dtoPage);
    }

}
