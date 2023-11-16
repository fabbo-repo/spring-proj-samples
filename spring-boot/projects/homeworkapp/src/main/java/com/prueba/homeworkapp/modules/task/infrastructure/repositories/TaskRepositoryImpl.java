package com.prueba.homeworkapp.modules.task.infrastructure.repositories;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.core.models.exceptions.EntityNotFoundException;
import com.prueba.homeworkapp.core.models.mapper.PageMapper;
import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.views.TaskFinishedJpaView;
import com.prueba.homeworkapp.modules.task.domain.repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.infrastructure.repositories.jpa.TaskJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${api.pagination.page-size}")
    private int pageSize;

    private final String sortField = TaskJpaEntity.SORT_FIELD;

    private final PageMapper<TaskJpaEntity> pageMapper = new PageMapper<>();

    @Override
    public boolean existsById(final UUID id) {
        return taskJpaRepository.existsById(id);
    }

    @Override
    public Optional<TaskJpaEntity> findById(final UUID id) {
        return taskJpaRepository.findById(id);
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
    public PageDto<TaskJpaEntity> findAll(final int pageNum) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(sortField).descending()
        );
        final Page<TaskJpaEntity> taskPage = taskJpaRepository
                .findAll(pageable);
        return pageMapper.entityToDto(taskPage);
    }

    @Override
    public PageDto<TaskJpaEntity> findAllByTaskStatus(
            final TaskStatusEnum taskStatus, final int pageNum
    ) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(sortField).descending()
        );
        final Page<TaskJpaEntity> taskPage = taskJpaRepository
                .findAllByTaskStatus(taskStatus, pageable);
        return pageMapper.entityToDto(taskPage);
    }

    @Override
    public void deleteById(final UUID id) {
        taskJpaRepository.deleteById(id);
    }

    @Override
    public TaskJpaEntity save(final TaskJpaEntity task) {
        return taskJpaRepository.save(task);
    }

    @Override
    public void saveTaskAsFinished(final UUID id) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaUpdate<TaskJpaEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(TaskJpaEntity.class);
        final Root<TaskJpaEntity> root = criteriaUpdate.from(TaskJpaEntity.class);

        criteriaUpdate.set(root.get("finished"), true);
        criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public PageDto<TaskJpaEntity> findAll(
            final String title,
            final String description,
            final Boolean finished,
            final LocalDateTime futureEstimatedDoneAt,
            final int pageNum
    ) {
        final Specification<TaskJpaEntity> specification = new Specification<TaskJpaEntity>() {
            @Override
            public Predicate toPredicate(
                    @NonNull
                    final Root<TaskJpaEntity> root,
                    @NonNull
                    final CriteriaQuery<?> query,
                    @NonNull
                    final CriteriaBuilder criteriaBuilder
            ) {
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
            }
        };
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(sortField).descending()
        );
        final Page<TaskJpaEntity> taskPage = taskJpaRepository
                .findAll(specification, pageable);
        return pageMapper.entityToDto(taskPage);
    }
}
