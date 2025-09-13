package com.hagag.shopsphere_ecommerce.strategy;

import com.hagag.shopsphere_ecommerce.entity.Order;
import com.hagag.shopsphere_ecommerce.enums.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("FAWRY")
public class FawryStrategy implements PaymentStrategy {

    private static final Logger log = LoggerFactory.getLogger(FawryStrategy.class);

    @Override
    public PaymentStatus pay(Order order, BigDecimal amount) {
        log.info("Processing Fawry Payment for order {}",order.getId());

        // Simulate payment success
        boolean success = true;

        if (success) {
            log.info("Fawry payment successful for order {}", order.getId());
            return PaymentStatus.SUCCESS;
        } else {
            log.info("Fawry payment failed for order {}", order.getId());
            return PaymentStatus.FAILED;
        }
    }

}
