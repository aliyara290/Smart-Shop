package com.aliyara.smartshop.strategy.discount;

import com.aliyara.smartshop.model.Order;

public interface DiscountStrategy {
    double apply(Order order);
}
