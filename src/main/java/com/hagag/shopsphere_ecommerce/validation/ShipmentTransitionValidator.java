package com.hagag.shopsphere_ecommerce.validation;

import com.hagag.shopsphere_ecommerce.enums.ShipmentStatus;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Map;

@Component
public class ShipmentTransitionValidator {

    private static final Map<ShipmentStatus, EnumSet<ShipmentStatus>> transitions = Map.of(
            ShipmentStatus.PENDING, EnumSet.of(ShipmentStatus.IN_TRANSIT, ShipmentStatus.CANCELLED),
            ShipmentStatus.IN_TRANSIT, EnumSet.of(ShipmentStatus.DELIVERED, ShipmentStatus.RETURNED, ShipmentStatus.CANCELLED),
            ShipmentStatus.DELIVERED, EnumSet.noneOf(ShipmentStatus.class),
            ShipmentStatus.RETURNED, EnumSet.noneOf(ShipmentStatus.class),
            ShipmentStatus.CANCELLED, EnumSet.noneOf(ShipmentStatus.class)
    );

    public boolean canTransition(ShipmentStatus current, ShipmentStatus target) {
        return transitions.getOrDefault(current, EnumSet.noneOf(ShipmentStatus.class)).contains(target);
    }
}
