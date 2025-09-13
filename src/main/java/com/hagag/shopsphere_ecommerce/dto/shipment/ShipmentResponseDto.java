package com.hagag.shopsphere_ecommerce.dto.shipment;

import com.hagag.shopsphere_ecommerce.enums.ShipmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShipmentResponseDto {

    private Long id;

    private Long orderId;

    private ShipmentStatus status;

    private String trackingNumber;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;

}
