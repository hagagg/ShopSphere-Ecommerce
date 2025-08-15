package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.authentication.AuthResponseDto;
import com.hagag.shopsphere_ecommerce.dto.authentication.LoginRequestDto;
import com.hagag.shopsphere_ecommerce.dto.authentication.RegisterRequestDto;
import jakarta.validation.Valid;

public interface AuthService {
    
    AuthResponseDto register(@Valid RegisterRequestDto registerRequestDto);

    AuthResponseDto login(@Valid LoginRequestDto loginRequestDto);
}
