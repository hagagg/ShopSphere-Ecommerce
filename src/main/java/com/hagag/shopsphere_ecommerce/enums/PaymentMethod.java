package com.hagag.shopsphere_ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentMethod {

    CREDIT_CARD, PAYPAL, FAWRY;

    @JsonCreator
    public static PaymentMethod fromString(String value) {
        return value == null ? null : PaymentMethod.valueOf(value.toUpperCase());
    }

}
