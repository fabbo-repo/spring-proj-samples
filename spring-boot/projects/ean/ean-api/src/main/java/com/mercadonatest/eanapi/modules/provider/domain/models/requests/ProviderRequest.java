package com.mercadonatest.eanapi.modules.provider.domain.models.requests;

import com.mercadonatest.eanapi.modules.provider.domain.models.enums.ProviderTypeEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProviderRequest(
        @NotNull
        ProviderTypeEnum type,
        @Min(0)
        @Max(9999999)
        int eanValue
) {
}
