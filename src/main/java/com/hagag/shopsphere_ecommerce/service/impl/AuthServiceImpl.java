package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.authentication.AuthResponseDto;
import com.hagag.shopsphere_ecommerce.dto.authentication.LoginRequestDto;
import com.hagag.shopsphere_ecommerce.dto.authentication.RegisterRequestDto;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.enums.UserRole;
import com.hagag.shopsphere_ecommerce.exception.custom.UserAlreadyExistsException;
import com.hagag.shopsphere_ecommerce.exception.custom.UserNotFoundException;
import com.hagag.shopsphere_ecommerce.mapper.AuthMapper;
import com.hagag.shopsphere_ecommerce.repository.UserRepo;
import com.hagag.shopsphere_ecommerce.security.JwtService;
import com.hagag.shopsphere_ecommerce.security.UserPrincipal;
import com.hagag.shopsphere_ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {

        Optional<User> existingUser = userRepo.findByUsernameOrEmail(registerRequestDto.getUsername(), registerRequestDto.getEmail());
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if(user.getUsername().equals(registerRequestDto.getUsername())) {
                throw new UserAlreadyExistsException("User with username: " + registerRequestDto.getUsername() + ", already exists");
            }
            if(user.getEmail().equals(registerRequestDto.getEmail())) {
                throw new UserAlreadyExistsException("User with email: " + registerRequestDto.getEmail() + ", already exists");
            }
        }

        User user = authMapper.toEntity(registerRequestDto);
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        if (user.getRole() == null) {
            user.setRole(UserRole.CUSTOMER);
        }
        user.setAddresses(new ArrayList<>());
        user.setOrders(new ArrayList<>());
        user.setCartItems(new ArrayList<>());

        userRepo.save(user);

        String token = jwtService.generateToken(new UserPrincipal(user));

        return new AuthResponseDto(token);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmailOrUsername() , loginRequestDto.getPassword()));

        User user = userRepo.findByUsernameOrEmail(loginRequestDto.getEmailOrUsername() , loginRequestDto.getEmailOrUsername())
                .orElseThrow(()-> new UserNotFoundException("User with username or email: " + loginRequestDto.getEmailOrUsername() + " not found"));

        String token = jwtService.generateToken(new UserPrincipal(user));

        return new AuthResponseDto(token);
    }
}
