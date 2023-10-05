package com.spyke.stripe.services;

import com.spyke.stripe.enums.CurrencyEnum;
import com.spyke.stripe.exceptions.PaymentException;
import com.spyke.stripe.exceptions.RequiresPaymentMethodException;
import com.spyke.stripe.exceptions.UnexpectedPaymentMethodException;
import com.spyke.stripe.requests.PaymentRequest;
import com.spyke.stripe.responses.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayService {
    @Value("${api.stripe.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public PaymentResponse pay(final PaymentRequest request) {
        final PaymentIntentCreateParams createParams = new
                PaymentIntentCreateParams.Builder()
                .setCurrency(CurrencyEnum.EUR.name())
                .setAmount(Long.parseLong(request.getSubsId()) * 100)
                .setDescription("Prueba 1")
                .setConfirm(true)
                .setPaymentMethod(request.getPayId())
                .build();

        final PaymentIntent intent;
        try {
            intent = PaymentIntent.create(createParams);
        } catch (final StripeException ex) {
            log.info("Stripe Exception: {}", ex.getUserMessage());
            throw new PaymentException(ex.getUserMessage());
        }
        return getPaymentResponseFromIntent(intent);
    }

    public PaymentResponse confirm(final PaymentRequest request) {
        final PaymentIntentCreateParams confirmParams = new
                PaymentIntentCreateParams.Builder()
                .setConfirm(true)
                .setPaymentMethod(request.getPayId())
                .build();

        final PaymentIntent intent;
        try {
            intent = PaymentIntent.create(confirmParams);
        } catch (final StripeException ex) {
            log.info("Stripe Exception: {}", ex.getUserMessage());
            throw new PaymentException(ex.getUserMessage());
        }
        return getPaymentResponseFromIntent(intent);
    }

    private PaymentResponse getPaymentResponseFromIntent(final PaymentIntent intent) {
        final String status = intent.getStatus();

        if ("requires_payment_method".equals(status)) {
            throw new RequiresPaymentMethodException();
        } else if (
                "requires_action".equals(status)
                || "requires_confirmation".equals(status)
                || "succeeded".equals(status)
        ) {
            return PaymentResponse
                    .builder()
                    .requiresAction(
                            "requires_action".equals(status)
                            || "requires_confirmation".equals(status)
                    )
                    .clientSecret(intent.getClientSecret())
                    .build();
        }
        throw new UnexpectedPaymentMethodException(status);
    }
}
