package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.payment.PaymentRequestDto;
import com.hagag.shopsphere_ecommerce.dto.payment.PaymentResponseDto;
import jakarta.validation.Valid;

public interface PaymentService {

    PaymentResponseDto pay(@Valid PaymentRequestDto paymentRequestDto);
}
