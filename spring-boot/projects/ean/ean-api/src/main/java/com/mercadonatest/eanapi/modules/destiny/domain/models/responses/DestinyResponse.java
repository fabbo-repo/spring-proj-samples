package com.mercadonatest.eanapi.modules.destiny.domain.models.responses;

import com.mercadonatest.eanapi.modules.destiny.domain.models.enums.DestinyTypeEnum;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record DestinyResponse(
        long id,
        int minEanValue,
        int maxEanValue,
        String address,
        DestinyTypeEnum type
) implements Serializable {
}
