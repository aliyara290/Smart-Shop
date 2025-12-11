package com.aliyara.smartshop.exception;

public class ExpiredPromoCodeException extends RuntimeException {
    public ExpiredPromoCodeException() {
        super("The promo code you entered has expired and can no longer be used.");
    }
}