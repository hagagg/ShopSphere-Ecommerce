package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.address.AddressRequestDto;
import com.hagag.shopsphere_ecommerce.dto.address.AddressResponseDto;
import com.hagag.shopsphere_ecommerce.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public AddressResponseDto createAddress(@Valid @RequestBody AddressRequestDto addressRequestDto) {
        return addressService.createAddress (addressRequestDto);

    }

    @GetMapping
    public List<AddressResponseDto> getMyAddress () {
        return addressService.getMyAddress ();
    }
}
