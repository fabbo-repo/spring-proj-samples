package com.mercadonatest.eanapi.modules.destiny.domain.services;

import com.mercadonatest.eanapi.core.config.RedisConfig;
import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.core.models.exceptions.ConflictEanValueException;
import com.mercadonatest.eanapi.core.models.exceptions.EntityNotFoundException;
import com.mercadonatest.eanapi.core.models.exceptions.MinMaxEanValueException;
import com.mercadonatest.eanapi.modules.destiny.domain.models.entities.DestinyJpaEntity;
import com.mercadonatest.eanapi.modules.destiny.domain.models.mappers.DestinyMapper;
import com.mercadonatest.eanapi.modules.destiny.domain.models.requests.DestinyRequest;
import com.mercadonatest.eanapi.modules.destiny.domain.models.responses.DestinyResponse;
import com.mercadonatest.eanapi.modules.destiny.domain.repositories.DestinyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DestinyServiceImpl implements DestinyService {

    private final DestinyRepository destinyRepository;

    private final DestinyMapper destinyMapper = DestinyMapper.INSTANCE;

    @Override
    public DestinyResponse create(final DestinyRequest request) {
        validateRequest(request);
        final DestinyJpaEntity entity = destinyRepository
                .save(destinyMapper.requestToEntity(request));
        return destinyMapper.entityToResponse(entity);
    }

    @Cacheable(value = RedisConfig.CACHE_GET_DESTINY_NAME, key = "#id")
    @Override
    public DestinyResponse get(final long id) {
        final DestinyJpaEntity entity = destinyRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                DestinyJpaEntity.TABLE_NAME,
                                DestinyJpaEntity.ID_COL,
                                id
                        )
                );
        return destinyMapper.entityToResponse(entity);
    }

    @Override
    public PageDto<DestinyResponse> list(final int pageNum) {
        return destinyRepository
                .findAll(pageNum)
                .map(destinyMapper::entityToResponse);
    }

    @Override
    public Void update(final long id, final DestinyRequest request) {
        validateRequest(request);
        final DestinyJpaEntity entity = destinyRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                DestinyJpaEntity.TABLE_NAME,
                                DestinyJpaEntity.ID_COL,
                                id
                        )
                );
        entity.setMinEanValue(request.minEanValue());
        entity.setMaxEanValue(request.maxEanValue());
        entity.setAddress(request.address());
        entity.setType(request.type());
        destinyRepository.save(entity);
        return null;
    }

    @Override
    public Void delete(final long id) {
        if (!destinyRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    DestinyJpaEntity.TABLE_NAME,
                    DestinyJpaEntity.ID_COL,
                    id
            );
        }
        destinyRepository.deleteById(id);
        return null;
    }

    private void validateRequest(final DestinyRequest request) {
        if (request.minEanValue() > request.maxEanValue()) {
            throw new MinMaxEanValueException();
        } else if (
                destinyRepository.existsByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
                        request.minEanValue(),
                        request.minEanValue()
                ) || destinyRepository.existsByMinEanValueLessThanEqualAndMaxEanValueGreaterThanEqual(
                        request.maxEanValue(),
                        request.maxEanValue()
                )
        ) {
            throw new ConflictEanValueException();
        }
    }
}
