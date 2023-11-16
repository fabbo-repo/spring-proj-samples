package com.mercadonatest.eanapi.modules.product.domain.models.responses;

import lombok.Builder;

@Builder
public record ProductResponse(
        Long id,
        String name,
        String description,
        double price,
        long stock,
        int eanValue,
        long providerId
) {
}
