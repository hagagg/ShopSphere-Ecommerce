package com.hagag.shopsphere_ecommerce.dto.user;

import com.hagag.shopsphere_ecommerce.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
}
