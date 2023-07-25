package com.prueba.homeworkapp.modules.task.infrastructure.repositories;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.core.models.exceptions.EntityNotFoundException;
import com.prueba.homeworkapp.core.models.mapper.PageMapper;
import com.prueba.homeworkapp.modules.task.domain.models.dtos.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.infrastructure.models.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.task.infrastructure.models.mappers.TaskEntityMapper;
import com.prueba.homeworkapp.modules.task.infrastructure.models.views.TaskFinishedJpaView;
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
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;

    @Value("${api.pagination.page-size}")
    private int pageSize;

    private final String sortField = TaskJpaEntity.SORT_FIELD;

    private final TaskEntityMapper taskMapper = TaskEntityMapper.INSTANCE;

    private final PageMapper<Task> pageMapper = new PageMapper<>();

    @Override
    public boolean existsById(final UUID id) {
        return taskJpaRepository.existsById(id);
    }

    @Override
    public Task findById(final UUID id) {
        final TaskJpaEntity taskJpaEntity = taskJpaRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                TaskJpaEntity.TABLE_NAME,
                                TaskJpaEntity.ID_COL,
                                id
                        )
                );
        return taskMapper.entityToDto(taskJpaEntity);
    }

    @Override
    public boolean isFinishedById(final UUID id) {
        final TaskFinishedJpaView taskJpaView = taskJpaRepository
                .findById(id, TaskFinishedJpaView.class)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                TaskJpaEntity.TABLE_NAME,
                                TaskJpaEntity.ID_COL,
                                id
                        )
                );
        return taskJpaView.getFinished();
    }

    @Override
    public PageDto<Task> findAll(final int pageNum) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(sortField).descending()
        );
        final Page<Task> taskPage = taskJpaRepository
                .findAll(pageable)
                .map(taskMapper::entityToDto);
        return pageMapper.enityToDto(taskPage);
    }

    @Override
    public PageDto<Task> findAllByTaskStatus(
            final TaskStatusEnum taskStatus, final int pageNum
    ) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(sortField).descending()
        );
        final Page<Task> taskPage = taskJpaRepository
                .findAllByTaskStatus(taskStatus, pageable)
                .map(taskMapper::entityToDto);
        return pageMapper.enityToDto(taskPage);
    }

    @Override
    public void deleteById(final UUID id) {
        taskJpaRepository.deleteById(id);
    }

    @Override
    public Task save(final Task task) {
        final TaskJpaEntity taskJpaEntity = taskMapper.dtoToEntity(task);
        return taskMapper.entityToDto(
                taskJpaRepository.save(taskJpaEntity)
        );
    }

    @Override
    public void saveTaskAsFinished(final UUID id) {
        taskJpaRepository.saveTaskAsFinished(id);
    }
}
