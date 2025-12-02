package com.aliyara.smartshop.strategy.discount;

import com.aliyara.smartshop.model.Order;
import com.aliyara.smartshop.model.PromoCode;
import com.aliyara.smartshop.service.interfaces.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PromoCodeDiscountStrategy implements DiscountStrategy {

    private final PromoCodeService promoCodeService;

    @Override
    public double apply(Order order) {
        double subtotal = order.getSubtotal();
        double discount = 0.0;
        if (order.getPromoCode() != null) {
            PromoCode promoCode = order.getPromoCode();
            switch (promoCode.getDiscountType()) {
                case PERCENTAGE -> {
                    discount = subtotal * (promoCode.getDiscountValue() / 100.0);
                    return discount;
                }
                case FIXED -> {
                    discount = Math.max(0, subtotal - promoCode.getDiscountValue());
                    return discount;
                }
            }
        }
        return discount;
    }
}
