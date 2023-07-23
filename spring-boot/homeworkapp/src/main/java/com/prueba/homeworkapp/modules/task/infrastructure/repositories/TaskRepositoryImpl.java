package com.prueba.homeworkapp.modules.task.infrastructure.repositories;

import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.views.TaskFinishedView;
import com.prueba.homeworkapp.modules.task.domain.repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.infrastructure.repositories.jpa.TaskJpaRepository;
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
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;

    @Value("${api.pagination.page-size}")
    private int pageSize;

    private final String sortField = Task.SORT_FIELD;

    @Override
    public boolean existsById(final UUID id) {
        return taskJpaRepository.existsById(id);
    }

    @Override
    public Optional<Task> findById(final UUID id) {
        return taskJpaRepository.findById(id);
    }

    @Override
    public Optional<TaskFinishedView> findFinishedViewById(UUID id) {
        return taskJpaRepository.findById(id, TaskFinishedView.class);
    }

    @Override
    public Page<Task> findAll(final int pageNum) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(sortField).descending()
        );
        return taskJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Task> findAllByTaskStatus(
            final TaskStatusEnum taskStatus, final int pageNum
    ) {
        final Pageable pageable = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(sortField).descending()
        );
        return taskJpaRepository.findAllByTaskStatus(taskStatus, pageable);
    }

    @Override
    public void deleteById(UUID id) {
        taskJpaRepository.deleteById(id);
    }

    @Override
    public Task save(Task task) {
        return taskJpaRepository.save(task);
    }

    @Override
    public void saveTaskAsFinished(UUID id) {
        taskJpaRepository.saveTaskAsFinished(id);
    }
}
