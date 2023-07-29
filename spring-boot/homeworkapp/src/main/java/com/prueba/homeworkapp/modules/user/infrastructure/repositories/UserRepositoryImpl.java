package com.prueba.homeworkapp.modules.user.infrastructure.repositories;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.core.models.exceptions.EntityNotFoundException;
import com.prueba.homeworkapp.core.models.mapper.PageMapper;
import com.prueba.homeworkapp.modules.task.infrastructure.models.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.domain.repositories.UserRepository;
import com.prueba.homeworkapp.modules.user.infrastructure.models.entities.UserJpaEntity;
import com.prueba.homeworkapp.modules.user.infrastructure.models.mappers.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Value("${api.pagination.page-size}")
    private int pageSize;

    private final String sortField = UserJpaEntity.SORT_FIELD;

    private final UserEntityMapper userMapper = UserEntityMapper.INSTANCE;

    private final PageMapper<User> pageMapper = new PageMapper<>();

    @Override
    public boolean existsById(UUID id) {
        return userJpaRepository.existsById(id);
    }

    @Override
    public User findById(UUID id) {
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
    public PageDto<User> findAll(int pageNum) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(sortField).descending()
        );
        final Page<User> userPage = userJpaRepository
                .findAll(pageable)
                .map(userMapper::entityToDto);
        return pageMapper.enityToDto(userPage);
    }

    @Override
    public void deleteById(UUID id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        final UserJpaEntity userJpaEntity = userMapper.dtoToEntity(user);
        return userMapper.entityToDto(
                userJpaRepository.save(userJpaEntity)
        );
    }
}
