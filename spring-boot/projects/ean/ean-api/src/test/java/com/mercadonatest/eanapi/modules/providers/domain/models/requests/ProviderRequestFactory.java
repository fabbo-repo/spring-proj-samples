package com.mercadonatest.eanapi.modules.providers.domain.models.requests;

import com.mercadonatest.eanapi.modules.provider.domain.models.requests.ProviderRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.mercadonatest.eanapi.TestUtils.randomInt;
import static com.mercadonatest.eanapi.TestUtils.randomProviderTypeEnum;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProviderRequestFactory {

    public static ProviderRequest.ProviderRequestBuilder providerRequestBuilder() {
        return ProviderRequest
                .builder()
                .type(randomProviderTypeEnum())
                .eanValue(randomInt(0, 9999999));
    }

    public static ProviderRequest providerRequest() {
        return providerRequestBuilder().build();
    }
}
