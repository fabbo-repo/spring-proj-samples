package com.prueba.homeworkapp.modules.user.infrastructure.repositories;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.core.models.exceptions.EntityNotFoundException;
import com.prueba.homeworkapp.core.models.mapper.PageMapper;
import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.domain.models.entities.UserJpaEntity;
import com.prueba.homeworkapp.modules.user.domain.models.mappers.UserMapper;
import com.prueba.homeworkapp.modules.user.domain.repositories.UserRepository;
import com.prueba.homeworkapp.modules.user.infrastructure.repositories.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;


@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Value("${api.pagination.page-size}")
    private int pageSize;

    private final String sortField = UserJpaEntity.SORT_FIELD;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    private final PageMapper<User> pageMapper = new PageMapper<>();

    @Override
    public boolean existsById(final UUID id) {
        return userJpaRepository.existsById(id);
    }

    @Override
    public User findById(final UUID id) {
        final UserJpaEntity userJpaEntity = userJpaRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                TaskJpaEntity.TABLE_NAME,
                                TaskJpaEntity.ID_COL,
                                id
                        )
                );
        return userMapper.entityToDto(userJpaEntity);
    }

    @Override
    public User findById(final UUID id, final Supplier<User> orElse) {
        final Optional<UserJpaEntity> userJpaEntity = userJpaRepository.findById(id);
        if (userJpaEntity.isPresent()) {
            return userMapper.entityToDto(userJpaEntity.get());
        }
        return orElse.get();
    }

    @Override
    public PageDto<User> findAll(final int pageNum) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(sortField).descending()
        );
        final Page<User> userPage = userJpaRepository
                .findAll(pageable)
                .map(userMapper::entityToDto);
        return pageMapper.entityToDto(userPage);
    }

    @Override
    public void deleteById(final UUID id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public User save(final User user) {
        final UserJpaEntity userJpaEntity = userMapper.dtoToEntity(user);
        userJpaEntity.setCreatedAt(user.getCreatedAt());
        userJpaEntity.setCreatedBy(user.getCreatedBy());
        return userMapper.entityToDto(
                userJpaRepository.save(userJpaEntity)
        );
    }
}
