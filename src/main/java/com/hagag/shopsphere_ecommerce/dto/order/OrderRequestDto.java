package com.hagag.shopsphere_ecommerce.dto.order;

import com.hagag.shopsphere_ecommerce.dto.orderitem.OrderItemRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    @Positive(message = "Shipping address ID must be a positive number")
    private Long shippingAddressId;

    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemRequestDto> orderItems;

}
