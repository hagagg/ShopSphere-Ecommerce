package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.authentication.AuthResponseDto;
import com.hagag.shopsphere_ecommerce.dto.authentication.LoginRequestDto;
import com.hagag.shopsphere_ecommerce.dto.authentication.RegisterRequestDto;
import com.hagag.shopsphere_ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseDto register (@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        return authService.register (registerRequestDto);
    }

    @PostMapping("/login")
    public AuthResponseDto login (@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return authService.login (loginRequestDto);
    }

}
