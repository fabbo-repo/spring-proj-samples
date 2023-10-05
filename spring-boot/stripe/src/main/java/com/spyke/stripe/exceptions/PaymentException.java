package com.spyke.stripe.exceptions;

public class PaymentException extends RuntimeException {
    public PaymentException(final String error) {
        super(String.format("Cannot create payment: %s", error));
    }
}
