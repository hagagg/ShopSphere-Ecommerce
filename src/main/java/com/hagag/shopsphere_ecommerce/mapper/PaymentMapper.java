package com.hagag.shopsphere_ecommerce.mapper;

import com.hagag.shopsphere_ecommerce.dto.payment.PaymentRequestDto;
import com.hagag.shopsphere_ecommerce.dto.payment.PaymentResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "orderId", source = "order.id")
    PaymentResponseDto toDto(Payment payment);

    @Mapping(target = "order.id", source = "orderId")
    Payment toEntity(PaymentRequestDto dto);
}
