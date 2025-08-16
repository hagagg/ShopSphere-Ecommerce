package com.hagag.shopsphere_ecommerce.dto.address;

import lombok.Data;

@Data
public class AddressResponseDto {

    private Long id;

    private Long userId;

    private String street;

    private String city;

    private String state;

    private String country;

    private boolean defaultAddress;

}
