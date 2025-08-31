package com.hagag.shopsphere_ecommerce.validation;

import com.hagag.shopsphere_ecommerce.entity.User;
import com.hagag.shopsphere_ecommerce.enums.UserRole;
import com.hagag.shopsphere_ecommerce.exception.custom.UnauthorizedActionException;
import com.hagag.shopsphere_ecommerce.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessGuard {

    private final SecurityUtil securityUtil;

    // Allow only current user
    public void checkUserOnly (User user) {
        User currentUser = securityUtil.getCurrentUser();

        if (!currentUser.getId().equals(user.getId()) ) {
            throw new UnauthorizedActionException("You are not allowed to do this action");
        }
    }

    // Allow current user or admin
    public void checkUserOrAdmin (User user) {
        User currentUser = securityUtil.getCurrentUser();

        if (!currentUser.getId().equals(user.getId()) && !currentUser.getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedActionException("You are not allowed to do this action");
        }
    }

    // Allow only admins
    public void checkAdminOnly () {
        User currentUser = securityUtil.getCurrentUser();

        if (!currentUser.getRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedActionException("You are not allowed to do this action");
        }
    }

}
