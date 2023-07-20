package com.prueba.homeworkapp.modules.task.domain.sevices;

import com.prueba.homeworkapp.core.domain.exceptions.ToDoException;
import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.mappers.TaskMapper;
import com.prueba.homeworkapp.modules.task.domain.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;
import com.prueba.homeworkapp.modules.task.domain.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(TaskRequest request) {
        final TaskMapper mapper = TaskMapper.INSTANCE;
        final Task createdTask = taskRepository.save(mapper.requestToEntity(request));
        return mapper.entityToResponse(createdTask);
    }

    @Override
    public Task findById(Long id) {
        Optional<Task> optionalTask = this.taskJpaRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);
        }
        return optionalTask.get();
    }

    @Override
    public List<Task> findAll() {
        return this.taskJpaRepository.findAll();
    }

    @Override
    public List<Task> findAllByTaskStatus(TaskStatusEnum status) {
        return this.taskJpaRepository.findAllByTaskStatus(status);
    }

    @Override
    @Transactional
    public void updateTaskAsFinished(Long id) {
        Optional<Task> optionalTask = this.taskJpaRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);
        }
        this.taskJpaRepository.markTaskAsFinished(id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Task> optionalTask = this.taskJpaRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);
        }
        this.taskJpaRepository.deleteById(id);
    }
}
