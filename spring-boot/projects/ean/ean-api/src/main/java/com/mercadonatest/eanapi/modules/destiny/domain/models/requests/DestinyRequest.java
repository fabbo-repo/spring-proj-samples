package com.mercadonatest.eanapi.modules.destiny.domain.models.requests;

import com.mercadonatest.eanapi.modules.destiny.domain.models.enums.DestinyTypeEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DestinyRequest(
        @Min(0)
        @Max(9)
        int minEanValue,
        @Min(0)
        @Max(9)
        int maxEanValue,
        @Size(max = 200)
        String address,
        @NotNull
        DestinyTypeEnum type
) {
}
