package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.payment.PaymentRequestDto;
import com.hagag.shopsphere_ecommerce.dto.payment.PaymentResponseDto;
import com.hagag.shopsphere_ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    public PaymentResponseDto pay (@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        log.info("Payment request received: orderId={}, method={}", paymentRequestDto.getOrderId(), paymentRequestDto.getMethod());

        return paymentService.pay(paymentRequestDto);
    }

}
