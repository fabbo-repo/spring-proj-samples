package com.prueba.homeworkapp.modules.task.infrastructure.output.repositories;

import com.prueba.homeworkapp.commons.mapper.ApiPageMapper;
import com.prueba.homeworkapp.commons.models.ApiPage;
import com.prueba.homeworkapp.modules.task.application.repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.mappers.TaskJpaMapper;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.repositories.TaskJpaRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;

    @Value("${api.pagination.page-size}")
    private int pageSize;

    private final ApiPageMapper<TaskJpaEntity> apiPageMapper = new ApiPageMapper<>();

    private static final TaskJpaMapper TASK_JPA_MAPPER = TaskJpaMapper.INSTANCE;

    @Override
    public boolean existsById(final UUID id) {
        return taskJpaRepository.existsById(id);
    }

    @Override
    public Optional<Task> findById(final UUID id) {
        return taskJpaRepository
                .findById(id)
                .map(TASK_JPA_MAPPER::taskEntityToTask);
    }

    @Override
    public ApiPage<Task> findAll(final int pageNum) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(
                        TaskJpaEntity.SORT_FIELD
                ).descending()
        );
        final Page<TaskJpaEntity> taskPage = taskJpaRepository
                .findAll(pageable);
        final ApiPage<TaskJpaEntity> apiPage = apiPageMapper
                .entityToDto(taskPage);
        return apiPage.map(
                TASK_JPA_MAPPER::taskEntityToTask
        );
    }

    @Override
    public ApiPage<Task> findAllByTaskStatus(
            final TaskStatusEnum taskStatus, final int pageNum
    ) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(
                        TaskJpaEntity.SORT_FIELD
                ).descending()
        );
        final Page<TaskJpaEntity> taskPage = taskJpaRepository
                .findAllByTaskStatus(taskStatus, pageable);
        final ApiPage<TaskJpaEntity> apiPage = apiPageMapper
                .entityToDto(taskPage);
        return apiPage.map(
                TASK_JPA_MAPPER::taskEntityToTask
        );
    }

    @Override
    public void deleteById(final UUID id) {
        taskJpaRepository.deleteById(id);
    }

    @Override
    public Task save(final Task task) {
        return TASK_JPA_MAPPER.taskEntityToTask(
                taskJpaRepository.save(
                        TASK_JPA_MAPPER.taskToTaskEntity(task)
                )
        );
    }

    @Override
    public ApiPage<Task> findAll(
            final String title,
            final String description,
            final Boolean finished,
            final LocalDateTime futureEstimatedDoneAt,
            final int pageNum
    ) {
        final Specification<TaskJpaEntity> specification = (
                @NonNull final Root<TaskJpaEntity> root,
                @NonNull final CriteriaQuery<?> query,
                @NonNull final CriteriaBuilder criteriaBuilder
        ) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (title != null) {
                predicates.add(
                        criteriaBuilder.like(
                                root.get("title"),
                                "%" + title + "%"
                        )
                );
            }
            if (description != null) {
                predicates.add(
                        criteriaBuilder.like(
                                root.get("description"),
                                "%" + description + "%"
                        )
                );
            }
            if (finished != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("finished"),
                                finished
                        )
                );
            }
            if (futureEstimatedDoneAt != null) {
                predicates.add(
                        criteriaBuilder.greaterThan(
                                root.get("estimatedDoneAt"),
                                futureEstimatedDoneAt
                        )
                );
            }
            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(
                        TaskJpaEntity.SORT_FIELD
                ).descending()
        );
        final Page<TaskJpaEntity> taskPage = taskJpaRepository
                .findAll(specification, pageable);
        final ApiPage<TaskJpaEntity> apiPage = apiPageMapper
                .entityToDto(taskPage);
        return apiPage.map(
                TASK_JPA_MAPPER::taskEntityToTask
        );
    }
}
