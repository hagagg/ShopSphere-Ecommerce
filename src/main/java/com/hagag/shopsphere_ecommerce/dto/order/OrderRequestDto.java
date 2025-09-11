package com.hagag.shopsphere_ecommerce.dto.order;

import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequestDto {

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    @NotNull(message = "Order status is required")
    private OrderStatus orderStatus;

    @NotNull(message = "Shipping address is required")
    @Positive(message = "Shipping address ID must be a positive number")
    private Long shippingAddressId;

}
