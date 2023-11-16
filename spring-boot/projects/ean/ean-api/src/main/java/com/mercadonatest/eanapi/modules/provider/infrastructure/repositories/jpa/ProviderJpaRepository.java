package com.mercadonatest.eanapi.modules.provider.infrastructure.repositories.jpa;

import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderJpaRepository extends JpaRepository<ProviderJpaEntity, Long> {
    boolean existsByEanValue(int eanValue);

    Optional<ProviderJpaEntity> findByEanValue(int eanValue);
}
