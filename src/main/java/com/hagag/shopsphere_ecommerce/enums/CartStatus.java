package com.hagag.shopsphere_ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CartStatus {
    ACTIVE,
    ORDERED,
    CANCELLED,
    PENDING;

    @JsonCreator
    public static CartStatus fromString(String value) {
        return value == null ? null : CartStatus.valueOf(value.toUpperCase());
    }
}
