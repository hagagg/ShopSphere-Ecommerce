package com.hagag.shopsphere_ecommerce.service.impl;

import com.hagag.shopsphere_ecommerce.entity.Address;
import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.exception.custom.ResourceNotFoundException;
import com.hagag.shopsphere_ecommerce.exception.custom.UnauthorizedActionException;
import com.hagag.shopsphere_ecommerce.repository.AddressRepo;
import com.hagag.shopsphere_ecommerce.service.ShippingService;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final SecurityUtil securityUtil;
    private final AddressRepo addressRepo;

    public Address resolveShippingAddress(Long addressId) {
        User currentUser = securityUtil.getCurrentUser();

        if (addressId != null) {
            Address address = addressRepo.findById(addressId)
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

            if (!address.getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedActionException("You are not allowed to use this address");
            }
            return address;
        } else {
            return currentUser.getAddresses()
                    .stream()
                    .filter(Address::isDefaultAddress)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("No default address found for this user"));
        }
    }

}
