package com.mercadonatest.eanapi.modules.product.infrastructure.repositories.jpa;

import com.mercadonatest.eanapi.modules.product.domain.models.entities.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    boolean existsByEanValue(final int eanValue);

    Optional<ProductJpaEntity> findByEanValue(final int eanValue);
}
