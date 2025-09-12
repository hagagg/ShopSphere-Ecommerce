package com.hagag.shopsphere_ecommerce.exception;

import com.hagag.shopsphere_ecommerce.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleValidation (MethodArgumentNotValidException ex){
        String errorMessage = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getField() + ": " + ex.getBindingResult().getFieldError().getDefaultMessage()
                : "Validation Failed";

        return ApiErrorResponse.builder()
                .success(false)
                .message("Validation Failed")
                .details(errorMessage)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiErrorResponse handleMissingRequestParam(MissingServletRequestParameterException ex) {
        return ApiErrorResponse.builder()
                .success(false)
                .message("Missing Request Parameter")
                .details(ex.getParameterName() + " is missing. it must not be blank")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ApiErrorResponse handleUnauthorized (UnauthorizedActionException ex) {
        return ApiErrorResponse.builder()
                .success(false)
                .message("Unauthorized Action")
                .details(ex.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiErrorResponse handleResourceNotFound (ResourceNotFoundException ex){
        return ApiErrorResponse.builder()
                .success(false)
                .message("Resource Not Found")
                .details(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ApiErrorResponse handleDuplicateResource (DuplicateResourceException ex){
        return ApiErrorResponse.builder()
                .success(false)
                .message("Resource Already Exists")
                .details(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(BusinessException.class)
    public ApiErrorResponse handleBusinessException(BusinessException ex) {
        return ApiErrorResponse.builder()
                .success(false)
                .message(ex.getStatus().getReasonPhrase())
                .details(ex.getMessage())
                .statusCode(ex.getStatus().value())
                .timestamp(LocalDateTime.now())
                .build();
    }

}
