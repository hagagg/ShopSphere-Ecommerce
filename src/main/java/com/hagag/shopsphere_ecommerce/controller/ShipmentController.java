package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.dto.shipment.ShipmentResponseDto;
import com.hagag.shopsphere_ecommerce.enums.ShipmentStatus;
import com.hagag.shopsphere_ecommerce.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PatchMapping("/{shipmentId}/status")
    public ShipmentResponseDto updateShipmentStatus(@PathVariable Long shipmentId, @RequestParam ShipmentStatus status) {

        return shipmentService.updateShipmentStatus(shipmentId, status);
    }

    @GetMapping("/{shipmentId}")
    public ShipmentResponseDto getShipment(@PathVariable Long shipmentId) {

        return shipmentService.getShipmentById(shipmentId);
    }

    @GetMapping
    public PaginatedResponseDto<ShipmentResponseDto> getAllShipments(Pageable pageable) {

        return shipmentService.getAllShipments(pageable);
    }

    @GetMapping("/my")
    public PaginatedResponseDto<ShipmentResponseDto> getMyShipments(Pageable pageable) {

        return shipmentService.getShipmentsForCurrentUser(pageable);
    }

    @PatchMapping("/{shipmentId}/cancel")
    public ShipmentResponseDto cancelShipment(@PathVariable Long shipmentId) {

        return shipmentService.cancelShipment(shipmentId);
    }


}
