package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.model.Order;

public interface DiscountService {
    double calculateDiscount(Order order);
}
