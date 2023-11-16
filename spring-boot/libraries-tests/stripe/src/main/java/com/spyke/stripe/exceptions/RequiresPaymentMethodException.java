package com.spyke.stripe.exceptions;

public class RequiresPaymentMethodException extends RuntimeException {
    public RequiresPaymentMethodException() {
        super("Requires payment method");
    }
}
