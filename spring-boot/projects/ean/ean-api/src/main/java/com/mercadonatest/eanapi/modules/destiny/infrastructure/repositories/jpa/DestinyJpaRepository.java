package com.mercadonatest.eanapi.modules.destiny.infrastructure.repositories.jpa;

import com.mercadonatest.eanapi.modules.destiny.domain.models.entities.DestinyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DestinyJpaRepository extends JpaRepository<DestinyJpaEntity, Long> {
    boolean existsByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
            final int minEanValue,
            final int maxEanValue
    );

    Optional<DestinyJpaEntity> findByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
            final int minEanValue,
            final int maxEanValue
    );
}
