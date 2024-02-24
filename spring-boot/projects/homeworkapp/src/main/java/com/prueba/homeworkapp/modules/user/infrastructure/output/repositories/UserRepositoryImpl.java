package com.prueba.homeworkapp.modules.user.infrastructure.output.repositories;

import com.prueba.homeworkapp.commons.mapper.ApiPageMapper;
import com.prueba.homeworkapp.commons.models.ApiPage;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.domain.models.entities.UserJpaEntity;
import com.prueba.homeworkapp.modules.user.domain.models.mappers.UserMapper;
import com.prueba.homeworkapp.modules.user.domain.repositories.UserRepository;
import com.prueba.homeworkapp.modules.user.infrastructure.output.repositories.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Value("${api.pagination.page-size}")
    private int pageSize;

    private static final UserMapper USER_MAPPER = UserMapper.INSTANCE;

    private final ApiPageMapper<User> apiPageMapper = new ApiPageMapper<>();

    @Override
    public boolean existsById(final UUID id) {
        return userJpaRepository.existsById(id);
    }

    @Override
    public Optional<UserJpaEntity> findById(final UUID id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public ApiPage<User> findAll(final int pageNum) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(
                        UserJpaEntity.SORT_FIELD
                ).descending()
        );
        final Page<User> userPage = userJpaRepository
                .findAll(pageable)
                .map(USER_MAPPER::entityToDto);
        return apiPageMapper.entityToDto(userPage);
    }

    @Override
    public void deleteById(final UUID id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public UserJpaEntity save(final UserJpaEntity userJpaEntity) {
        userJpaEntity.setCreatedAt(userJpaEntity.getCreatedAt());
        userJpaEntity.setCreatedBy(userJpaEntity.getCreatedBy());
        return userJpaRepository.save(userJpaEntity);
    }
}
