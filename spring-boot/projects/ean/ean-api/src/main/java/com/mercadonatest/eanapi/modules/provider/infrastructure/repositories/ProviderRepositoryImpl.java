package com.mercadonatest.eanapi.modules.provider.infrastructure.repositories;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.core.models.mappers.PageMapper;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import com.mercadonatest.eanapi.modules.provider.domain.repositories.ProviderRepository;
import com.mercadonatest.eanapi.modules.provider.infrastructure.repositories.jpa.ProviderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProviderRepositoryImpl implements ProviderRepository {

    private final ProviderJpaRepository providerJpaRepository;

    private final PageMapper<ProviderJpaEntity> pageMapper = new PageMapper<>();

    @Value("${api.pagination.page-size}")
    private int pageSize;

    @Override
    public boolean existsById(final Long id) {
        return providerJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByEanValue(int eanValue) {
        return providerJpaRepository.existsByEanValue(eanValue);
    }

    @Override
    public Optional<ProviderJpaEntity> findById(final Long id) {
        return providerJpaRepository.findById(id);
    }

    @Override
    public Optional<ProviderJpaEntity> findByEanValue(final int eanValue) {
        return providerJpaRepository.findByEanValue(eanValue);
    }

    @Override
    public PageDto<ProviderJpaEntity> findAll(int pageNum) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(ProviderJpaEntity.SORT_FIELD).descending()
        );
        final Page<ProviderJpaEntity> page = providerJpaRepository
                .findAll(pageable);
        return pageMapper.entityToDto(page);
    }

    @Override
    public void deleteById(final Long id) {
        providerJpaRepository.deleteById(id);
    }

    @Override
    public ProviderJpaEntity save(final ProviderJpaEntity provider) {
        return providerJpaRepository.save(provider);
    }
}
