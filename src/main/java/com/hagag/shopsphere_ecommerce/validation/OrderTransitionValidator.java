package com.hagag.shopsphere_ecommerce.validation;

import com.hagag.shopsphere_ecommerce.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Map;

@Component
public class OrderTransitionValidator {

    private static final Map<OrderStatus, EnumSet<OrderStatus>> transitions = Map.of(
            OrderStatus.PENDING, EnumSet.of(OrderStatus.PAID, OrderStatus.CANCELLED),
            OrderStatus.PAID, EnumSet.of(OrderStatus.PROCESSING, OrderStatus.CANCELLED),
            OrderStatus.PROCESSING, EnumSet.of(OrderStatus.SHIPPED, OrderStatus.CANCELLED),
            OrderStatus.SHIPPED, EnumSet.of(OrderStatus.DELIVERED),
            OrderStatus.DELIVERED, EnumSet.noneOf(OrderStatus.class),
            OrderStatus.CANCELLED, EnumSet.noneOf(OrderStatus.class)
    );

    public boolean canTransition(OrderStatus current, OrderStatus target) {
        return transitions.getOrDefault(current, EnumSet.noneOf(OrderStatus.class)).contains(target);
    }

}
