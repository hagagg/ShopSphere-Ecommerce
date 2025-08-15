package com.hagag.shopsphere_ecommerce.exception;

import com.hagag.shopsphere_ecommerce.exception.custom.UserAlreadyExistsException;
import com.hagag.shopsphere_ecommerce.exception.custom.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ApiErrorResponse handleUserAlreadyExists(UserAlreadyExistsException ex){
        return ApiErrorResponse.builder()
                .success(false)
                .message("User Already Exists")
                .details(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ApiErrorResponse handleUserNotFound(UserNotFoundException ex){
        return ApiErrorResponse.builder()
                .success(false)
                .message("User Not Found")
                .details(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
