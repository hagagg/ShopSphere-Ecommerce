package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.category.CategoryRequestDto;
import com.hagag.shopsphere_ecommerce.dto.category.CategoryResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Category;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.mapper.CategoryMapper;
import com.hagag.shopsphere_ecommerce.repository.CategoryRepo;
import com.hagag.shopsphere_ecommerce.service.CategoryService;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final AccessGuard accessGuard;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        accessGuard.checkAdminOnly();

        Category category = categoryMapper.toEntity(categoryRequestDto);

        category = categoryRepo.save(category);

        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto getCategoryById(long categoryId) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category with id: " + categoryId + "Not Found"));

        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {

        return categoryRepo.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
        accessGuard.checkAdminOnly();

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category with id: " + categoryId + "Not Found"));

        category.setName(categoryRequestDto.getName());
        category.setDescription(categoryRequestDto.getDescription());

        Category updatedCategory = categoryRepo.save(category);

        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        accessGuard.checkAdminOnly();

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category with id: " + categoryId + "Not Found"));

         categoryRepo.delete(category);
    }

    @Override
    public List<CategoryResponseDto> searchCategoriesByName(String name) {
        return categoryRepo.findByNameContainingIgnoreCase(name)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

}
