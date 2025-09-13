package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.dto.shipment.ShipmentResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Address;
import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.entity.Shipment;
import com.hagag.shopsphere_ecommerce.enums.ShipmentStatus;
import org.springframework.data.domain.Pageable;

public interface ShipmentService {

    Address resolveShippingAddress(Long addressId);

    Shipment createShipment(Order order);

    ShipmentResponseDto updateShipmentStatus(Long shipmentId, ShipmentStatus status);

    ShipmentResponseDto getShipmentById(Long shipmentId);

    PaginatedResponseDto<ShipmentResponseDto> getAllShipments(Pageable pageable);

    PaginatedResponseDto<ShipmentResponseDto> getShipmentsForCurrentUser(Pageable pageable);

    ShipmentResponseDto cancelShipment(Long shipmentId);

    ShipmentResponseDto markAsReturned(Long shipmentId);

    }
