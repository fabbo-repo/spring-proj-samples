package com.mercadonatest.eanapi.modules.destiny.domain.repositories;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.modules.destiny.domain.models.entities.DestinyJpaEntity;

import java.util.Optional;

public interface DestinyRepository {
    boolean existsById(final Long id);

    boolean existsByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
            final int minEanValue,
            final int maxEanValue
    );

    Optional<DestinyJpaEntity> findById(final Long id);

    Optional<DestinyJpaEntity> findByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
            final int minEanValue,
            final int maxEanValue
    );

    PageDto<DestinyJpaEntity> findAll(final int pageNum);

    void deleteById(final Long id);

    DestinyJpaEntity save(final DestinyJpaEntity entity);
}