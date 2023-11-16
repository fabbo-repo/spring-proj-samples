package com.spyke.stripe.exceptions;

public class UnexpectedPaymentMethodException extends RuntimeException {
    public UnexpectedPaymentMethodException(final String paymentMethod) {
        super(String.format("Unexpected payment method: %s", paymentMethod));
    }
}
