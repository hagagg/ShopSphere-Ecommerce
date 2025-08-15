package com.hagag.shopsphere_ecommerce.exception.custom;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }

}
