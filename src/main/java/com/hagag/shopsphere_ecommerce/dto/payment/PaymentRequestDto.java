package com.hagag.shopsphere_ecommerce.dto.payment;

import com.hagag.shopsphere_ecommerce.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDto {

    @NotNull (message = "Payment method required")
    private PaymentMethod method;

    @NotNull (message = "You must provide the order")
    private Long orderId;

}
