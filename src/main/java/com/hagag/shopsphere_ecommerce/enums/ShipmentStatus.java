package com.hagag.shopsphere_ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ShipmentStatus {

    PENDING,
    IN_TRANSIT,
    DELIVERED,
    RETURNED,
    CANCELLED;

    @JsonCreator
    public static ShipmentStatus fromString(String value) {
        return value == null ? null : ShipmentStatus.valueOf(value.toUpperCase());
    }

}
