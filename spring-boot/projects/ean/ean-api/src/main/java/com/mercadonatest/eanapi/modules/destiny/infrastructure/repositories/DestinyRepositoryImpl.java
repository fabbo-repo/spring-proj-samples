package com.mercadonatest.eanapi.modules.destiny.infrastructure.repositories;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.core.models.mappers.PageMapper;
import com.mercadonatest.eanapi.modules.destiny.domain.models.entities.DestinyJpaEntity;
import com.mercadonatest.eanapi.modules.destiny.domain.repositories.DestinyRepository;
import com.mercadonatest.eanapi.modules.destiny.infrastructure.repositories.jpa.DestinyJpaRepository;
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
public class DestinyRepositoryImpl implements DestinyRepository {

    private final DestinyJpaRepository destinyJpaRepository;

    private final PageMapper<DestinyJpaEntity> pageMapper = new PageMapper<>();

    @Value("${api.pagination.page-size}")
    private int pageSize;

    @Override
    public boolean existsById(final Long id) {
        return destinyJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
            final int minEanValue, final int maxEanValue
    ) {
        return destinyJpaRepository.existsByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
                minEanValue,
                maxEanValue
        );
    }

    @Override
    public Optional<DestinyJpaEntity> findById(final Long id) {
        return destinyJpaRepository.findById(id);
    }

    @Override
    public Optional<DestinyJpaEntity> findByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
            int minEanValue, int maxEanValue
    ) {
        return destinyJpaRepository.findByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
                minEanValue,
                maxEanValue
        );
    }

    @Override
    public PageDto<DestinyJpaEntity> findAll(int pageNum) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(DestinyJpaEntity.SORT_FIELD).descending()
        );
        final Page<DestinyJpaEntity> page = destinyJpaRepository
                .findAll(pageable);
        return pageMapper.entityToDto(page);
    }

    @Override
    public void deleteById(final Long id) {
        destinyJpaRepository.deleteById(id);
    }

    @Override
    public DestinyJpaEntity save(final DestinyJpaEntity entity) {
        return destinyJpaRepository.save(entity);
    }
}
