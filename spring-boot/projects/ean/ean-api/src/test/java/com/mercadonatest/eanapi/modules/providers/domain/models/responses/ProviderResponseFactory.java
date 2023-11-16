package com.mercadonatest.eanapi.modules.providers.domain.models.responses;

import com.mercadonatest.eanapi.modules.provider.domain.models.responses.ProviderResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.mercadonatest.eanapi.TestUtils.randomInt;
import static com.mercadonatest.eanapi.TestUtils.randomLong;
import static com.mercadonatest.eanapi.TestUtils.randomProviderTypeEnum;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProviderResponseFactory {

    public static ProviderResponse.ProviderResponseBuilder providerResponseBuilder() {
        return ProviderResponse
                .builder()
                .id(randomLong())
                .type(randomProviderTypeEnum())
                .eanValue(randomInt(0, 9999999));
    }

    public static ProviderResponse providerResponse() {
        return providerResponseBuilder().build();
    }
}
