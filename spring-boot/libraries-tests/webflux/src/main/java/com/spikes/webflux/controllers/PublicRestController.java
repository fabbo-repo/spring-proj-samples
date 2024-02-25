package com.spikes.webflux.controllers;

import com.spikes.webflux.models.ApiExampleEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/public")
public class PublicRestController {

    @GetMapping("nothing")
    public Mono<ResponseEntity<Void>> getNothing() {
        return Mono.just(
                ResponseEntity.noContent()
                              .build()
        );
    }

    @GetMapping("/{id}")
    public Mono<ApiExampleEntity> getApiExampleEntity(
            @PathVariable String id
    ) {
        return Mono.just(
                new ApiExampleEntity("public example entity" + id)
        );
    }

    @GetMapping
    public Flux<ApiExampleEntity> listApiExampleEntity() {
        return Flux.just(
                new ApiExampleEntity("1"),
                new ApiExampleEntity("2"),
                new ApiExampleEntity("3")
        );
    }
}
