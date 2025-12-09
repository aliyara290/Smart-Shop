package com.aliyara.smartshop.strategy.discount;

import com.aliyara.smartshop.enums.ClientLoyaltyLevel;
import com.aliyara.smartshop.model.Client;
import com.aliyara.smartshop.model.Order;
import com.aliyara.smartshop.service.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoyaltyDiscountStrategy implements DiscountStrategy {

    private final ClientService clientService;

    @Override
    public double apply(Order order) {
        return calculateLoyaltyDiscount(order.getClient().getLoyaltyLevel(), order.getSubtotal());
    }

    public double calculateLoyaltyDiscount(ClientLoyaltyLevel loyaltyLevel, double orderSubTotal) {
        switch (loyaltyLevel) {
            case SILVER -> {
                return orderSubTotal >= 500.0 ? 5.0 * (orderSubTotal / 100) : 0.0;
            }
            case GOLD -> {
                return orderSubTotal >= 800.0 ? 10.0 * (orderSubTotal / 100) : 0.0;
            }
            case PLATINUM -> {
                return orderSubTotal >= 1200.0 ? 15.0 * (orderSubTotal / 100) : 0.0;
            }
            default -> {
                return 0.0;
            }
        }
    }

}
