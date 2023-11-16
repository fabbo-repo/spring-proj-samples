package com.mercadonatest.eanapi.modules.provider.domain.services;

import com.mercadonatest.eanapi.core.config.RedisConfig;
import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.core.models.exceptions.ConflictEanValueException;
import com.mercadonatest.eanapi.core.models.exceptions.EntityNotFoundException;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import com.mercadonatest.eanapi.modules.provider.domain.models.mappers.ProviderMapper;
import com.mercadonatest.eanapi.modules.provider.domain.models.requests.ProviderRequest;
import com.mercadonatest.eanapi.modules.provider.domain.models.responses.ProviderResponse;
import com.mercadonatest.eanapi.modules.provider.domain.repositories.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    private final ProviderMapper providerMapper = ProviderMapper.INSTANCE;

    @Override
    public ProviderResponse create(final ProviderRequest request) {
        validateRequest(request);
        final ProviderJpaEntity entity = providerRepository
                .save(providerMapper.requestToEntity(request));
        return providerMapper.entityToResponse(entity);
    }

    @Cacheable(value = RedisConfig.CACHE_GET_PROVIDER_NAME, key = "#id")
    @Override
    public ProviderResponse get(final long id) {
        final ProviderJpaEntity entity = providerRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                ProviderJpaEntity.TABLE_NAME,
                                ProviderJpaEntity.ID_COL,
                                id
                        )
                );
        return providerMapper.entityToResponse(entity);
    }

    @Override
    public PageDto<ProviderResponse> list(final int pageNum) {
        return providerRepository
                .findAll(pageNum)
                .map(providerMapper::entityToResponse);
    }

    @Override
    public Void update(final long id, final ProviderRequest request) {
        validateRequest(request);
        final ProviderJpaEntity entity = providerRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                ProviderJpaEntity.TABLE_NAME,
                                ProviderJpaEntity.ID_COL,
                                id
                        )
                );
        entity.setEanValue(request.eanValue());
        entity.setType(request.type());
        providerRepository.save(entity);
        return null;
    }

    @Override
    public Void delete(final long id) {
        if (!providerRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    ProviderJpaEntity.TABLE_NAME,
                    ProviderJpaEntity.ID_COL,
                    id
            );
        }
        providerRepository.deleteById(id);
        return null;
    }

    private void validateRequest(final ProviderRequest request) {
        if (
                providerRepository.existsByEanValue(
                        request.eanValue()
                )
        ) {
            throw new ConflictEanValueException();
        }
    }
}
