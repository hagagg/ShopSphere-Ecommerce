package com.hagag.shopsphere_ecommerce.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressRequestDto {

    @NotBlank
    @Size(max = 50)
    private String street;

    @NotBlank
    @Size(max = 50)
    private String city;

    @NotBlank
    @Size(max = 50)
    private String state;

    @NotBlank
    @Size(max = 50)
    private String country;

}
