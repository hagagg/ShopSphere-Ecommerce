package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.payment.PaymentRequestDto;
import com.hagag.shopsphere_ecommerce.dto.payment.PaymentResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.entity.Payment;
import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import com.hagag.shopsphere_ecommerce.enums.PaymentStatus;
import com.hagag.shopsphere_ecommerce.exception.custom.BusinessException;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.mapper.PaymentMapper;
import com.hagag.shopsphere_ecommerce.repository.OrderRepo;
import com.hagag.shopsphere_ecommerce.repository.PaymentRepo;
import com.hagag.shopsphere_ecommerce.service.PaymentService;
import com.hagag.shopsphere_ecommerce.service.ShipmentService;
import com.hagag.shopsphere_ecommerce.strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final OrderRepo orderRepo;
    private final Map<String, PaymentStrategy> strategies;
    private final PaymentRepo paymentRepo;
    private final PaymentMapper paymentMapper;
    private final ShipmentService shipmentService;


    @Override
    @Transactional
    public PaymentResponseDto pay(PaymentRequestDto paymentRequestDto) {
        log.info("Starting payment process for orderId={} using {}", paymentRequestDto.getOrderId(), paymentRequestDto.getMethod());

        Order order = orderRepo.findById(paymentRequestDto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found"));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            log.warn("Order {} is  not pending. Current status: {}", paymentRequestDto.getOrderId(), order.getOrderStatus());

            throw new BusinessException("Can't pay for this order" , HttpStatus.BAD_REQUEST);
        }

        PaymentStrategy strategy = strategies.get(paymentRequestDto.getMethod().name());

        if (strategy == null) {
            log.warn("Unsupported payment method: {}", paymentRequestDto.getMethod());

            throw new BusinessException("Unsupported payment method", HttpStatus.BAD_REQUEST);
        }

        PaymentStatus status = strategy.pay(order , order.getTotalAmount());

        Payment payment = Payment.builder()
                .order(order)
                .amount(order.getTotalAmount())
                .status(status)
                .method(paymentRequestDto.getMethod())
                .build();

        order.setPayment(payment);

        if (status.equals(PaymentStatus.SUCCESS)) {
            order.setOrderStatus(OrderStatus.PAID);
        }

        shipmentService.createShipment(order);

        paymentRepo.save(payment);
        orderRepo.save(order);

        log.info("Payment processed: orderId={}, paymentId={}, status={}", order.getId(), payment.getId(), status);

        return paymentMapper.toDto(payment);
    }

}
