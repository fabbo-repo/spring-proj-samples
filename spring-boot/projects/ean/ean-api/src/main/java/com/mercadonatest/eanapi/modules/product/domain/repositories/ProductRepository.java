package com.mercadonatest.eanapi.modules.product.domain.repositories;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.modules.product.domain.models.entities.ProductJpaEntity;

import java.util.Optional;

public interface ProductRepository {
    boolean existsById(final Long id);

    boolean existsByEanValue(final int eanValue);

    Optional<ProductJpaEntity> findById(final Long id);

    Optional<ProductJpaEntity> findByEanValue(final int eanValue);

    PageDto<ProductJpaEntity> findAll(final int pageNum);

    void deleteById(final Long id);

    ProductJpaEntity save(final ProductJpaEntity entity);
}