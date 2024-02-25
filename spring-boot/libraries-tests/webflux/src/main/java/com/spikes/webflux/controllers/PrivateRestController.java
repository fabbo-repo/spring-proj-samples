package com.spikes.webflux.controllers;

import com.spikes.webflux.models.ApiExampleEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/private")
public class PrivateRestController {
    @GetMapping
    public Flux<ApiExampleEntity> listApiExampleEntity() {
        return Flux.just(
                new ApiExampleEntity("1"),
                new ApiExampleEntity("2"),
                new ApiExampleEntity("3")
        );
    }
}
