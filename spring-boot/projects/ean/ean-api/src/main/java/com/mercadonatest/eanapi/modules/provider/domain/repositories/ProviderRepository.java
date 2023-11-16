package com.mercadonatest.eanapi.modules.provider.domain.repositories;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;

import java.util.Optional;

public interface ProviderRepository {
    boolean existsById(final Long id);

    boolean existsByEanValue(final int eanValue);

    Optional<ProviderJpaEntity> findById(final Long id);

    Optional<ProviderJpaEntity> findByEanValue(final int eanValue);

    PageDto<ProviderJpaEntity> findAll(final int pageNum);

    void deleteById(final Long id);

    ProviderJpaEntity save(final ProviderJpaEntity entity);
}