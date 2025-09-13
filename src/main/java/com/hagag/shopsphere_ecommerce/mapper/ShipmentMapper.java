package com.hagag.shopsphere_ecommerce.mapper;

import com.hagag.shopsphere_ecommerce.dto.shipment.ShipmentResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    @Mapping(source = "order.id", target = "orderId")
    ShipmentResponseDto toDto(Shipment shipment);

}
