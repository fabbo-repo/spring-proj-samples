package com.mercadonatest.eanapi.modules.providers.domain.models.entities;

import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.mercadonatest.eanapi.TestUtils.randomInt;
import static com.mercadonatest.eanapi.TestUtils.randomLong;
import static com.mercadonatest.eanapi.TestUtils.randomProviderTypeEnum;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProviderJpaEntityFactory {

    public static ProviderJpaEntity.ProviderJpaEntityBuilder providerJpaEntityBuilder() {
        return ProviderJpaEntity
                .builder()
                .id(randomLong())
                .type(randomProviderTypeEnum())
                .eanValue(randomInt());
    }

    public static ProviderJpaEntity providerJpaEntity() {
        return providerJpaEntityBuilder().build();
    }
}
