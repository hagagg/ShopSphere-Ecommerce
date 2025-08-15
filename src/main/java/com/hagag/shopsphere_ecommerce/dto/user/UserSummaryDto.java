package com.hagag.shopsphere_ecommerce.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDto {

    private Long id;
    private String userName;
    private String fullName;
}
