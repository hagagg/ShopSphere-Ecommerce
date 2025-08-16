package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.address.AddressRequestDto;
import com.hagag.shopsphere_ecommerce.dto.address.AddressResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface AddressService {

    AddressResponseDto createAddress(@Valid AddressRequestDto addressRequestDto);

    List<AddressResponseDto> getMyAddress();

    List<AddressResponseDto> getUserAddresses(Long userId);

    AddressResponseDto updateAddress(Long addressId, @Valid AddressRequestDto addressRequestDto);

    void deleteAddress(Long addressId);

    AddressResponseDto setDefaultAddress(Long addressId);
}
