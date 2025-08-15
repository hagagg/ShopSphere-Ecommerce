package com.hagag.shopsphere_ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {

    private boolean success;
    private String message;
    private int statusCode;
    private LocalDateTime timestamp;
    private String details;

}
