package com.hagag.shopsphere_ecommerce.dto.pagination;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedResponseDto<T>{

    private List<T> items;
    private int currentPage;
    private int totalPages;
    private Long totalItems;
    private int pageSize;
    private boolean isLastPage;
}
