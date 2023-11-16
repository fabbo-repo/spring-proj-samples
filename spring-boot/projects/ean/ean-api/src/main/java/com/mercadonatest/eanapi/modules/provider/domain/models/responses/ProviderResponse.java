package com.mercadonatest.eanapi.modules.provider.domain.models.responses;

import com.mercadonatest.eanapi.modules.provider.domain.models.enums.ProviderTypeEnum;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ProviderResponse(
        Long id,
        ProviderTypeEnum type,
        int eanValue
) implements Serializable {
}
