package com.prueba.homeworkapp.modules.task.application.controllers;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.modules.task.application.models.mappers.TaskControllerMapper;
import com.prueba.homeworkapp.modules.task.application.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.application.models.responses.TaskResponse;
import com.prueba.homeworkapp.modules.task.domain.models.dtos.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.sevices.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/task")
@Validated
@Tag(name = "Task API")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final TaskControllerMapper taskMapper = TaskControllerMapper.INSTANCE;

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable final UUID id) {
        final Task task = taskService.getTask(id);
        return taskMapper.dtoToResponse(task);
    }

    @GetMapping
    public PageDto<TaskResponse> getTasks(
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final PageDto<Task> taskPage = taskService.getTasks(page);
        return taskPage.map(taskMapper::dtoToResponse);
    }

    @GetMapping("/status/{status}")
    public PageDto<TaskResponse> getTasksByTaskStatus(
            @PathVariable final TaskStatusEnum status,
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final PageDto<Task> taskPage = taskService.getTasksByTaskStatus(status, page);
        return taskPage.map(taskMapper::dtoToResponse);
    }

    @PostMapping
    public TaskResponse createTask(@RequestBody @Valid final TaskRequest request) {
        final Task task = taskMapper.requestToDto(request);
        return taskMapper.dtoToResponse(
                taskService.createTask(task)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable final UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/mark-as-finished/{id}")
    public ResponseEntity<Void> updateTaskAsFinished(@PathVariable final UUID id) {
        taskService.updateTaskAsFinished(id);
        return ResponseEntity.noContent().build();
    }
}
