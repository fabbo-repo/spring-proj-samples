package com.mercadonatest.eanapi.modules.destiny.domain.models.mappers;

import com.mercadonatest.eanapi.modules.destiny.domain.models.entities.DestinyJpaEntity;
import com.mercadonatest.eanapi.modules.destiny.domain.models.requests.DestinyRequest;
import com.mercadonatest.eanapi.modules.destiny.domain.models.responses.DestinyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DestinyMapper {
    DestinyMapper INSTANCE = Mappers.getMapper(DestinyMapper.class);

    @Mapping(target = "id", ignore = true)
    DestinyJpaEntity requestToEntity(final DestinyRequest request);

    DestinyResponse entityToResponse(final DestinyJpaEntity entity);
}
