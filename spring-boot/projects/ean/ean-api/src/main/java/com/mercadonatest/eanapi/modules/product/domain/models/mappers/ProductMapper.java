package com.mercadonatest.eanapi.modules.product.domain.models.mappers;

import com.mercadonatest.eanapi.modules.product.domain.models.entities.ProductJpaEntity;
import com.mercadonatest.eanapi.modules.product.domain.models.requests.ProductRequest;
import com.mercadonatest.eanapi.modules.product.domain.models.responses.ProductResponse;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "provider", ignore = true)
    ProductJpaEntity requestToEntity(final ProductRequest request);

    @Mapping(source = "provider", target = "providerId", qualifiedByName = "providerToId")
    ProductResponse entityToResponse(final ProductJpaEntity entity);

    @Named("providerToId")
    default long providerToId(final ProviderJpaEntity provider) {
        return provider.getId();
    }
}
