package com.prueba.homeworkapp.modules.task.domain.sevices;

import com.prueba.homeworkapp.core.domain.exceptions.EntityNotFoundException;
import com.prueba.homeworkapp.core.domain.exceptions.PageNotFoundException;
import com.prueba.homeworkapp.core.domain.mapper.PageMapper;
import com.prueba.homeworkapp.core.domain.responses.PageResponse;
import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.exceptions.TaskAlreadyFinishedException;
import com.prueba.homeworkapp.modules.task.domain.models.mappers.TaskMapper;
import com.prueba.homeworkapp.modules.task.domain.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;
import com.prueba.homeworkapp.modules.task.domain.models.views.TaskFinishedView;
import com.prueba.homeworkapp.modules.task.domain.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper = TaskMapper.INSTANCE;

    private final PageMapper pageMapper = PageMapper.INSTANCE;

    @Override
    public TaskResponse createTask(final TaskRequest request) {
        final Task createdTask = taskRepository.save(
                taskMapper.requestToEntity(request)
        );
        return taskMapper.entityToResponse(createdTask);
    }

    @Override
    public TaskResponse getTask(final UUID id) {
        final Task task = taskRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                Task.TABLE_NAME,
                                Task.ID_COL,
                                id
                        )
                );
        return taskMapper.entityToResponse(task);
    }

    @Override
    public PageResponse<TaskResponse> getTasks(final int pageNum) {
        final Page<TaskResponse> page = taskRepository
                .findAll(pageNum)
                .map(taskMapper::entityToResponse);
        return checkAndMapPage(page, pageNum);
    }

    @Override
    public PageResponse<TaskResponse> getTasksByTaskStatus(final TaskStatusEnum status, final int pageNum) {
        final Page<TaskResponse> page = taskRepository
                .findAllByTaskStatus(status, pageNum)
                .map(taskMapper::entityToResponse);
        return checkAndMapPage(page, pageNum);
    }

    @Override
    public void deleteTask(final UUID id) {
        checkTaskExists(id);
        taskRepository.deleteById(id);
    }

    @Override
    public void updateTaskAsFinished(final UUID id) {
        final TaskFinishedView taskFinishedView = taskRepository
                .findFinishedViewById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                Task.TABLE_NAME,
                                Task.ID_COL,
                                id
                        )
                );
        if (taskFinishedView.getFinished()) {
            throw new TaskAlreadyFinishedException(
                    Task.ID_COL,
                    id
            );
        }
        taskRepository.saveTaskAsFinished(id);
    }

    private void checkTaskExists(final UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    Task.TABLE_NAME,
                    Task.ID_COL,
                    id
            );
        }
    }

    private PageResponse<TaskResponse> checkAndMapPage(final Page<TaskResponse> page, final int pageNum) {
        if (page.getTotalPages() < pageNum) {
            throw new PageNotFoundException(
                    pageNum,
                    Task.TABLE_NAME
            );
        }
        return pageMapper.taskPageToResponse(page);
    }
}
