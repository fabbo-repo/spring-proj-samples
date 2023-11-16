package com.mercadonatest.eanapi.modules.ean.domain.models.responses;

import com.mercadonatest.eanapi.modules.provider.domain.models.enums.ProviderTypeEnum;
import lombok.Builder;

@Builder
public record EanResponse(
        Long providerId,
        ProviderTypeEnum providerType,
        int providerEan,
        Long productId,
        String productName,
        String productDescription,
        double productPrice,
        long productStock,
        int productEan,
        Long destinyId,
        int destinyEan,
        String destinyAddress,
        String destinyType
) {
}
