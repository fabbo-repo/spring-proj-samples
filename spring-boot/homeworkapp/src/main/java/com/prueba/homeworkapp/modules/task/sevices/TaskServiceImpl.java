package com.prueba.homeworkapp.modules.task.sevices;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.core.models.exceptions.EntityNotFoundException;
import com.prueba.homeworkapp.modules.task.models.dtos.Task;
import com.prueba.homeworkapp.modules.task.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.models.exceptions.TaskAlreadyFinishedException;
import com.prueba.homeworkapp.modules.task.repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.models.entities.TaskJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(final Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getTask(final UUID id) {
        return taskRepository.findById(id);
    }

    @Override
    public PageDto<Task> getTasks(final int pageNum) {
        return taskRepository.findAll(pageNum);
    }

    @Override
    public PageDto<Task> getTasksByTaskStatus(final TaskStatusEnum status, final int pageNum) {
        return taskRepository.findAllByTaskStatus(status, pageNum);
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
