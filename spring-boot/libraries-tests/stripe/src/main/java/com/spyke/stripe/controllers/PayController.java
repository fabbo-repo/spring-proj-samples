package com.spyke.stripe.controllers;

import com.spyke.stripe.requests.PaymentRequest;
import com.spyke.stripe.responses.PaymentResponse;
import com.spyke.stripe.services.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
@Slf4j
public class PayController {
    private final PayService payService;

    @PostMapping
    public PaymentResponse pay(@RequestBody final PaymentRequest request) {
        log.info("Pay Request: {}", request.toString());
        return payService.pay(request);
    }

    @PostMapping(path = "/confirm")
    public PaymentResponse confirm(@RequestBody final PaymentRequest request) {
        log.info("Confirm Request: {}", request.toString());
        return payService.confirm(request);
    }
}