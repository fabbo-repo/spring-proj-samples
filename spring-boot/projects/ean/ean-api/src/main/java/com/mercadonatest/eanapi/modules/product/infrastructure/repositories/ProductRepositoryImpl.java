package com.mercadonatest.eanapi.modules.product.infrastructure.repositories;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.core.models.mappers.PageMapper;
import com.mercadonatest.eanapi.modules.product.domain.models.entities.ProductJpaEntity;
import com.mercadonatest.eanapi.modules.product.domain.repositories.ProductRepository;
import com.mercadonatest.eanapi.modules.product.infrastructure.repositories.jpa.ProductJpaRepository;
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
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    private final PageMapper<ProductJpaEntity> pageMapper = new PageMapper<>();

    @Value("${api.pagination.page-size}")
    private int pageSize;

    @Override
    public boolean existsById(final Long id) {
        return productJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByEanValue(final int eanValue) {
        return productJpaRepository.existsByEanValue(eanValue);
    }

    @Override
    public Optional<ProductJpaEntity> findById(final Long id) {
        return productJpaRepository.findById(id);
    }

    @Override
    public Optional<ProductJpaEntity> findByEanValue(int eanValue) {
        return productJpaRepository.findByEanValue(eanValue);
    }

    @Override
    public PageDto<ProductJpaEntity> findAll(int pageNum) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(ProductJpaEntity.SORT_FIELD).descending()
        );
        final Page<ProductJpaEntity> page = productJpaRepository
                .findAll(pageable);
        return pageMapper.entityToDto(page);
    }

    @Override
    public void deleteById(final Long id) {
        productJpaRepository.deleteById(id);
    }

    @Override
    public ProductJpaEntity save(final ProductJpaEntity entity) {
        return productJpaRepository.save(entity);
    }
}
