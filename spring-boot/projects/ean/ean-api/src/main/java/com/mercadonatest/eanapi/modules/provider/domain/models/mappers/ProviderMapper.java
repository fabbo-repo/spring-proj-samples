package com.mercadonatest.eanapi.modules.provider.domain.models.mappers;

import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import com.mercadonatest.eanapi.modules.provider.domain.models.requests.ProviderRequest;
import com.mercadonatest.eanapi.modules.provider.domain.models.responses.ProviderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProviderMapper {
    ProviderMapper INSTANCE = Mappers.getMapper(ProviderMapper.class);

    @Mapping(target = "id", ignore = true)
    ProviderJpaEntity requestToEntity(final ProviderRequest request);

    ProviderResponse entityToResponse(final ProviderJpaEntity entity);
}
