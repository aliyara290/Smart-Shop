package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.model.Order;
import com.aliyara.smartshop.service.interfaces.DiscountService;
import com.aliyara.smartshop.strategy.discount.LoyaltyDiscountStrategy;
import com.aliyara.smartshop.strategy.discount.PromoCodeDiscountStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiscountServiceImpl implements DiscountService {

    private final LoyaltyDiscountStrategy loyaltyDiscountStrategy;
    private final PromoCodeDiscountStrategy promoCodeDiscountStrategy;

    @Override
    public double calculateDiscount(Order order) {
        log.debug("im inside Discount service");
        double subtotal = order.getSubtotal();
        log.debug("subtotal {}", subtotal);
        double loyaltyDiscount = loyaltyDiscountStrategy.apply(order);

        if(loyaltyDiscount > 0) {
            subtotal -= loyaltyDiscount;
        }
        double promoCodeDiscount = promoCodeDiscountStrategy.apply(order);

        if(promoCodeDiscount > 0) {
            subtotal -= promoCodeDiscount;
        }

        return order.getSubtotal() - subtotal;
    }
}
