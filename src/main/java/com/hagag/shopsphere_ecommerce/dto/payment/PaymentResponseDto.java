package com.hagag.shopsphere_ecommerce.dto.payment;

import com.hagag.shopsphere_ecommerce.enums.PaymentMethod;
import com.hagag.shopsphere_ecommerce.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentResponseDto {

    private Long id;

    private PaymentStatus status;

    private PaymentMethod method;

    private Long orderId;

}
