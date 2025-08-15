package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.dto.address.AddressRequestDto;
import com.hagag.shopsphere_ecommerce.dto.address.AddressResponseDto;
import com.hagag.shopsphere_ecommerce.entity.Address;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.mapper.AddressMapper;
import com.hagag.shopsphere_ecommerce.repository.AddressRepo;
import com.hagag.shopsphere_ecommerce.service.AddressService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final AddressMapper addressMapper;
    private final SecurityUtil securityUtil;

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


}
