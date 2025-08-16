package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.address.AddressRequestDto;
import com.hagag.shopsphere_ecommerce.dto.address.AddressResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Address;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.mapper.AddressMapper;
import com.hagag.shopsphere_ecommerce.repository.AddressRepo;
import com.hagag.shopsphere_ecommerce.service.AddressService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import com.hagag.shopsphere_ecommerce.validation.AccessGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final AddressMapper addressMapper;
    private final SecurityUtil securityUtil;
    private final AccessGuard accessGuard;

    @Override
    public AddressResponseDto createAddress(AddressRequestDto addressRequestDto) {
        User user = securityUtil.getCurrentUser();

        Address address = addressMapper.toEntity(addressRequestDto);
        address.setUser(user);

        Address savedAddress = addressRepo.save(address);

        return addressMapper.toDto(savedAddress);
    }

    @Override
    public List<AddressResponseDto> getMyAddress() {
        User user = securityUtil.getCurrentUser();

        return addressRepo.findByUser (user)
                .stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddressResponseDto> getUserAddresses(Long userId) {
        accessGuard.checkAdminOnly();

        return addressRepo.findByUser_Id(userId)
                .stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponseDto updateAddress(Long addressId, AddressRequestDto addressRequestDto) {
        Address address = addressRepo.findById(addressId).orElseThrow(()-> new ResourceNotFoundException("Address not found with id: " + addressId));

        accessGuard.checkUserOnly(address.getUser());

        address.setStreet(addressRequestDto.getStreet());
        address.setCity(addressRequestDto.getCity());
        address.setState(addressRequestDto.getState());
        address.setCountry(addressRequestDto.getCountry());

        Address updated = addressRepo.save(address);

        return addressMapper.toDto(updated);
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = addressRepo.findById(addressId).orElseThrow(()-> new ResourceNotFoundException("Address not found with id: " + addressId));

        accessGuard.checkUserOnly(address.getUser());

        addressRepo.delete(address);
    }

    @Override
    @Transactional
    public AddressResponseDto setDefaultAddress(Long addressId) {
        Address address = addressRepo.findById(addressId).orElseThrow(()-> new ResourceNotFoundException("Address not found with id: " + addressId));

        accessGuard.checkUserOnly(address.getUser());

        addressRepo.findByUser_IdAndDefaultAddressTrue(address.getUser().getId())
                .forEach(prevDefault -> prevDefault.setDefaultAddress(false));

        address.setDefaultAddress(true);

        Address updated = addressRepo.save(address);

        return addressMapper.toDto(updated);
    }


}
