package com.hagag.shopsphere_ecommerce.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Email or Username is required")
    private String emailOrUsername;

    @NotBlank(message = "Password is required")
    @Size(min=8, message = "Password must be at least 8 characters long")
    private String password;
}
