package com.mercadonatest.eanapi.modules.ean.domain.services;

import com.mercadonatest.eanapi.core.config.RedisConfig;
import com.mercadonatest.eanapi.core.models.exceptions.EntityNotFoundException;
import com.mercadonatest.eanapi.modules.destiny.domain.models.entities.DestinyJpaEntity;
import com.mercadonatest.eanapi.modules.destiny.domain.repositories.DestinyRepository;
import com.mercadonatest.eanapi.modules.ean.domain.models.mappers.EanMapper;
import com.mercadonatest.eanapi.modules.ean.domain.models.responses.EanResponse;
import com.mercadonatest.eanapi.modules.product.domain.models.entities.ProductJpaEntity;
import com.mercadonatest.eanapi.modules.product.domain.repositories.ProductRepository;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import com.mercadonatest.eanapi.modules.provider.domain.models.enums.ProviderTypeEnum;
import com.mercadonatest.eanapi.modules.provider.domain.repositories.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EanServiceImpl implements EanService {

    private final EanMapper destinyMapper = EanMapper.INSTANCE;

    private final ProviderRepository providerRepository;

    private final ProductRepository productRepository;

    private final DestinyRepository destinyRepository;

    @Cacheable(value = RedisConfig.CACHE_GET_EAN_NAME, key = "#eanValue")
    @Override
    public EanResponse get(final long eanValue) {
        // Ean value parsing to different entities ean codes
        final String eanValueStr = Long.toString(eanValue);
        final int providerEanValue = eanValueStr.length() <= 6
                                     ? 0 : Integer.parseInt(eanValueStr.substring(
                0,
                eanValueStr.length() - 6
        ));
        final int productEanValue = eanValueStr.length() > 6
                                    ? Integer.parseInt(eanValueStr.substring(
                eanValueStr.length() - 6,
                eanValueStr.length() - 1
        )) : eanValueStr.length() <= 1 ? 0 : Integer.parseInt(eanValueStr.substring(
                0,
                eanValueStr.length() - 1
        ));
        final int destinyEanValue = Character.getNumericValue(
                eanValueStr.charAt(eanValueStr.length() - 1)
        );

        // Once ean codes has been parsed, entities can be fetched
        final ProviderJpaEntity provider = providerRepository
                .findByEanValue(providerEanValue)
                .orElse(
                        ProviderJpaEntity
                                .builder()
                                .eanValue(providerEanValue)
                                .type(ProviderTypeEnum.MERCADONA)
                                .build()
                );
        final ProductJpaEntity product = productRepository
                .findByEanValue(productEanValue)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                ProductJpaEntity.TABLE_NAME,
                                ProductJpaEntity.EAN_VALUE_COL,
                                productEanValue
                        )
                );
        final DestinyJpaEntity destiny = destinyRepository
                .findByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
                        destinyEanValue,
                        destinyEanValue
                )
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                DestinyJpaEntity.TABLE_NAME,
                                String.format(
                                        "%s - %s",
                                        DestinyJpaEntity.MIN_EAN_VALUE_COL,
                                        DestinyJpaEntity.MAX_EAN_VALUE_COL
                                ),
                                destinyEanValue
                        )
                );

        return destinyMapper.entityToResponse(
                provider,
                product,
                destiny,
                destinyEanValue
        );
    }
}
