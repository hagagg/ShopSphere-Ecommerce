package com.hagag.shopsphere_ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {
    PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED;

    @JsonCreator
    public static OrderStatus fromString(String value) {
        return value == null ? null : OrderStatus.valueOf(value.toUpperCase());
    }
}
