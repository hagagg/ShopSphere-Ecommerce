package com.hagag.shopsphere_ecommerce.mapper;

import com.hagag.shopsphere_ecommerce.dto.category.CategoryRequestDto;
import com.hagag.shopsphere_ecommerce.dto.category.CategoryResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity (CategoryRequestDto categoryRequestDto);

    CategoryResponseDto toDto (Category category);
}
