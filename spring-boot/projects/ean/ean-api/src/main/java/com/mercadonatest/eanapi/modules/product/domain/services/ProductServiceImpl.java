package com.mercadonatest.eanapi.modules.product.domain.services;

import com.mercadonatest.eanapi.core.config.RedisConfig;
import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.core.models.exceptions.ConflictEanValueException;
import com.mercadonatest.eanapi.core.models.exceptions.EntityNotFoundException;
import com.mercadonatest.eanapi.modules.product.domain.models.entities.ProductJpaEntity;
import com.mercadonatest.eanapi.modules.product.domain.models.mappers.ProductMapper;
import com.mercadonatest.eanapi.modules.product.domain.models.requests.ProductRequest;
import com.mercadonatest.eanapi.modules.product.domain.models.responses.ProductResponse;
import com.mercadonatest.eanapi.modules.product.domain.repositories.ProductRepository;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import com.mercadonatest.eanapi.modules.provider.domain.repositories.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProviderRepository providerRepository;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public ProductResponse create(final ProductRequest request) {
        validateRequest(request);
        final ProviderJpaEntity providerEntity = providerRepository
                .findById(request.providerId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                ProviderJpaEntity.TABLE_NAME,
                                ProviderJpaEntity.ID_COL,
                                request.providerId()
                        )
                );

        final ProductJpaEntity newEntity = productMapper.requestToEntity(request);
        newEntity.setProvider(providerEntity);

        final ProductJpaEntity entity = productRepository
                .save(newEntity);
        return productMapper.entityToResponse(entity);
    }

    @Cacheable(value = RedisConfig.CACHE_GET_PRODUCT_NAME, key = "#id")
    @Override
    public ProductResponse get(final long id) {
        final ProductJpaEntity entity = productRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                ProductJpaEntity.TABLE_NAME,
                                ProductJpaEntity.ID_COL,
                                id
                        )
                );
        return productMapper.entityToResponse(entity);
    }

    @Override
    public PageDto<ProductResponse> list(final int pageNum) {
        return productRepository
                .findAll(pageNum)
                .map(productMapper::entityToResponse);
    }

    @Override
    public Void update(final long id, final ProductRequest request) {
        validateRequest(request);
        final ProductJpaEntity entity = productRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                ProductJpaEntity.TABLE_NAME,
                                ProductJpaEntity.ID_COL,
                                id
                        )
                );
        final ProviderJpaEntity providerEntity = providerRepository
                .findById(request.providerId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                ProviderJpaEntity.TABLE_NAME,
                                ProviderJpaEntity.ID_COL,
                                request.providerId()
                        )
                );
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        entity.setStock(request.stock());
        entity.setEanValue(request.eanValue());
        entity.setProvider(providerEntity);
        productRepository.save(entity);
        return null;
    }

    @Override
    public Void delete(final long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    ProductJpaEntity.TABLE_NAME,
                    ProductJpaEntity.ID_COL,
                    id
            );
        }
        productRepository.deleteById(id);
        return null;
    }

    private void validateRequest(final ProductRequest request) {
        if (
                productRepository.existsByEanValue(request.eanValue())
        ) {
            throw new ConflictEanValueException();
        }
    }
}
