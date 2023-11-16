package com.mercadonatest.eanapi.modules.ean.domain.models.mappers;

import com.mercadonatest.eanapi.modules.destiny.domain.models.entities.DestinyJpaEntity;
import com.mercadonatest.eanapi.modules.ean.domain.models.responses.EanResponse;
import com.mercadonatest.eanapi.modules.product.domain.models.entities.ProductJpaEntity;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EanMapper {
    EanMapper INSTANCE = Mappers.getMapper(EanMapper.class);

    @Mapping(source = "provider.id", target = "providerId")
    @Mapping(source = "provider.type", target = "providerType")
    @Mapping(source = "provider.eanValue", target = "providerEan")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.description", target = "productDescription")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "product.stock", target = "productStock")
    @Mapping(source = "product.eanValue", target = "productEan")
    @Mapping(source = "destiny.id", target = "destinyId")
    @Mapping(source = "destinyEanValue", target = "destinyEan")
    @Mapping(source = "destiny.address", target = "destinyAddress")
    @Mapping(source = "destiny.type", target = "destinyType")
    EanResponse entityToResponse(
            final ProviderJpaEntity provider,
            final ProductJpaEntity product,
            final DestinyJpaEntity destiny,
            final int destinyEanValue
    );
}
