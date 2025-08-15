package com.hagag.shopsphere_ecommerce.exception.custom;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
