package com.hagag.shopsphere_ecommerce.exception.custom;

public class UnauthorizedActionException extends RuntimeException{

    public UnauthorizedActionException(String message) {
        super(message);
    }
}
