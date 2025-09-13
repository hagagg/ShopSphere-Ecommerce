package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.dto.shipment.ShipmentResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Address;
import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.entity.Shipment;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.enums.ShipmentStatus;
import com.hagag.shopsphere_ecommerce.exception.custom.BusinessException;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.exception.custom.UnauthorizedActionException;
import com.hagag.shopsphere_ecommerce.mapper.PaginationMapper;
import com.hagag.shopsphere_ecommerce.mapper.ShipmentMapper;
import com.hagag.shopsphere_ecommerce.repository.AddressRepo;
import com.hagag.shopsphere_ecommerce.repository.ShipmentRepo;
import com.hagag.shopsphere_ecommerce.service.ShipmentService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import com.hagag.shopsphere_ecommerce.validation.ShipmentTransitionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final SecurityUtil securityUtil;
    private final AddressRepo addressRepo;
    private final ShipmentRepo shipmentRepo;
    private final AccessGuard accessGuard;
    private final ShipmentTransitionValidator shipmentTransitionValidator;
    private final ShipmentMapper shipmentMapper;
    private final PaginationMapper paginationMapper;

    public Address resolveShippingAddress(Long addressId) {
        User currentUser = securityUtil.getCurrentUser();

        if (addressId != null) {
            Address address = addressRepo.findById(addressId)
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

            if (!address.getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedActionException("You are not allowed to use this address");
            }
            return address;
        } else {
            return currentUser.getAddresses()
                    .stream()
                    .filter(Address::isDefaultAddress)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("No default address found for this user"));
        }
    }

    private String generateTrackingNumber(Long orderId) {
        return "ORD-" + orderId + "-" + System.currentTimeMillis();
    }

    @Override
    public Shipment createShipment(Order order) {

        Shipment shipment = Shipment.builder()
                .order(order)
                .trackingNumber(generateTrackingNumber(order.getId()))
                .status(ShipmentStatus.PENDING)
                .shippedAt(null)
                .deliveredAt(null)
                .build();

        return shipmentRepo.save(shipment);
    }

    @Override
    public ShipmentResponseDto updateShipmentStatus(Long shipmentId, ShipmentStatus status) {
        accessGuard.checkAdminOnly();

        Shipment shipment = shipmentRepo.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        if (!shipmentTransitionValidator.canTransition(shipment.getStatus(), status)) {
            throw new BusinessException("Invalid shipment status transition: " + shipment.getStatus() + " -> " + status , HttpStatus.BAD_REQUEST);
        }

        shipment.setStatus(status);

        if (status == ShipmentStatus.IN_TRANSIT) {
            shipment.setShippedAt(LocalDateTime.now());
        } else if (status == ShipmentStatus.DELIVERED) {
            shipment.setDeliveredAt(LocalDateTime.now());
        }

        shipmentRepo.save(shipment);

        return shipmentMapper.toDto(shipment);
    }

    @Override
    public ShipmentResponseDto getShipmentById(Long shipmentId) {
        accessGuard.checkAdminOnly();

        Shipment shipment = shipmentRepo.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        return shipmentMapper.toDto(shipment);
    }

    @Override
    public PaginatedResponseDto<ShipmentResponseDto> getAllShipments(Pageable pageable) {
        accessGuard.checkAdminOnly();

        Page<Shipment> shipments = shipmentRepo.findAll(pageable);

        Page<ShipmentResponseDto> dtoPage = shipments.map(shipmentMapper::toDto);

        return paginationMapper.toPaginatedResponse(dtoPage);
    }

    @Override
    public PaginatedResponseDto<ShipmentResponseDto> getShipmentsForCurrentUser(Pageable pageable) {
        User user = securityUtil.getCurrentUser();

        Page<Shipment> shipments = shipmentRepo.findByOrder_User(user, pageable);

        Page<ShipmentResponseDto> dtoPage = shipments.map(shipmentMapper::toDto);

        return paginationMapper.toPaginatedResponse(dtoPage);
    }

    @Override
    public ShipmentResponseDto cancelShipment(Long shipmentId) {
        accessGuard.checkAdminOnly();

        Shipment shipment = shipmentRepo.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        // Only allow cancellation if PENDING or IN_TRANSIT
        if (shipment.getStatus() != ShipmentStatus.PENDING && shipment.getStatus() != ShipmentStatus.IN_TRANSIT) {

            throw new BusinessException("Cannot cancel shipment at this stage: " + shipment.getStatus(), HttpStatus.BAD_REQUEST);
        }

        shipment.setStatus(ShipmentStatus.CANCELLED);
        shipmentRepo.save(shipment);

        return shipmentMapper.toDto(shipment);
    }

    public ShipmentResponseDto markAsReturned(Long shipmentId) {
        accessGuard.checkAdminOnly();

        Shipment shipment = shipmentRepo.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        if (shipment.getStatus() != ShipmentStatus.DELIVERED) {

            throw new BusinessException("Only delivered shipments can be marked as returned", HttpStatus.BAD_REQUEST);
        }

        shipment.setStatus(ShipmentStatus.RETURNED);
        shipmentRepo.save(shipment);

        return shipmentMapper.toDto(shipment);
    }

}
