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
        log.debug("inside DiscountService, subtotal={}", order.getSubtotal());

        double totalDiscount = 0.0;

        double loyaltyDiscount = loyaltyDiscountStrategy.apply(order);
        totalDiscount += loyaltyDiscount;

        double promoDiscount = promoCodeDiscountStrategy.apply(order);
        totalDiscount += promoDiscount;

        return totalDiscount;
    }
}
