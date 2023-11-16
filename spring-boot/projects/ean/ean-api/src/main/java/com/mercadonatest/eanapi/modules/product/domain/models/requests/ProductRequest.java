package com.mercadonatest.eanapi.modules.product.domain.models.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProductRequest(
        @NotBlank
        @Size(max = 20)
        String name,
        @Size(max = 200)
        String description,
        @Min(0)
        double price,
        @Min(0)
        long stock,
        @Min(0)
        @Max(99999)
        int eanValue,
        long providerId
) {
}
