package com.prueba.homeworkapp.modules.task.infrastructure.repositories;

import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.infrastructure.repositories.jpa.TaskJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;

    @Override
    public List<Task> findAllByTaskStatus(final TaskStatusEnum taskStatusEnum) {
        return taskJpaRepository.findAllByTaskStatus(taskStatusEnum);
    }

    @Override
    public Task save(final Task task) {
        return taskJpaRepository.save(task);
    }
}
