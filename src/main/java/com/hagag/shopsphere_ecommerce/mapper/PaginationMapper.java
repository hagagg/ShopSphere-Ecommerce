package com.hagag.shopsphere_ecommerce.mapper;

import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationMapper {

    public <T> PaginatedResponseDto<T> toPaginatedResponse (Page<T> page) {
        return PaginatedResponseDto.<T>builder()
                .items(page.getContent())
                .currentPage(page.getNumber() + 1)
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .pageSize(page.getSize())
                .isLastPage(page.isLast())
                .build();
    }
}
