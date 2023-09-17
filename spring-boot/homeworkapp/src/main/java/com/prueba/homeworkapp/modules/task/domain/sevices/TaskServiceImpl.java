package com.prueba.homeworkapp.modules.task.domain.sevices;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.core.models.exceptions.EntityNotFoundException;
import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.exceptions.TaskAlreadyFinishedException;
import com.prueba.homeworkapp.modules.task.domain.models.mappers.TaskMapper;
import com.prueba.homeworkapp.modules.task.domain.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;
import com.prueba.homeworkapp.modules.task.domain.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper = TaskMapper.INSTANCE;

    @Override
    public TaskResponse createTask(final TaskRequest task) {
        final TaskJpaEntity taskJpaEntity = taskRepository
                .save(taskMapper.requestToEntity(task));
        return taskMapper.entityToResponse(taskJpaEntity);
    }

    @Override
    public TaskResponse getTask(final UUID id) {
        final TaskJpaEntity taskJpaEntity = taskRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                TaskJpaEntity.TABLE_NAME,
                                TaskJpaEntity.ID_COL,
                                id
                        )
                );
        return taskMapper.entityToResponse(taskJpaEntity);
    }

    @Override
    public PageDto<TaskResponse> getTasks(final int pageNum) {
        return taskRepository
                .findAll(pageNum)
                .map(taskMapper::entityToResponse);
    }

    @Override
    public PageDto<TaskResponse> getTasksByTaskStatus(final TaskStatusEnum status, final int pageNum) {
        return taskRepository
                .findAllByTaskStatus(status, pageNum)
                .map(taskMapper::entityToResponse);
    }

    @Override
    public void deleteTask(final UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    TaskJpaEntity.TABLE_NAME,
                    TaskJpaEntity.ID_COL,
                    id
            );
        }
        taskRepository.deleteById(id);
    }

    @Override
    public void updateTaskAsFinished(final UUID id) {
        if (taskRepository.isFinishedById(id)) {
            throw new TaskAlreadyFinishedException(
                    TaskJpaEntity.ID_COL,
                    id
            );
        }
        taskRepository.saveTaskAsFinished(id);
    }
}
