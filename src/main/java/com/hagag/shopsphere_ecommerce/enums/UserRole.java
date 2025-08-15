package com.hagag.shopsphere_ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserRole {
    ADMIN, CUSTOMER, VENDOR;

    @JsonCreator
    public static UserRole fromString(String value) {
        return value == null ? null : UserRole.valueOf(value.toUpperCase());
    }
}
