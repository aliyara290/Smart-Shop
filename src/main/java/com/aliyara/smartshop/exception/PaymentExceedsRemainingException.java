package com.aliyara.smartshop.exception;

public class PaymentExceedsRemainingException extends RuntimeException {
    public PaymentExceedsRemainingException(String message) {
        super(message);
    }
}
