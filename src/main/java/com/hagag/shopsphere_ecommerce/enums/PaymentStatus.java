package com.hagag.shopsphere_ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentStatus {

    PENDING, SUCCESS, FAILED, CANCELLED;

    @JsonCreator
    public static PaymentStatus fromString(String value) {
        return value == null ? null : PaymentStatus.valueOf(value.toUpperCase());
    }

}
