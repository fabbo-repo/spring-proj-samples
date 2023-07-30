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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = TaskController.CONTROLLER_PATH)
@Validated
@Tag(name = "Task API")
@RequiredArgsConstructor
public class TaskController {
    public static final String CONTROLLER_PATH = "/task";
    public static final String GET_TASK_SUB_PATH = "/{id}";
    public static final String GET_TASK_BY_TASK_STATUS_SUB_PATH = "/status/{status}";
    public static final String DEL_TASK_SUB_PATH = "/{id}";
    public static final String PUT_MARK_AS_FINISHED_SUB_PATH = "/mark-as-finished/{id}";

    private final TaskService taskService;

    private final TaskControllerMapper taskMapper = TaskControllerMapper.INSTANCE;

    @GetMapping(GET_TASK_SUB_PATH)
    public ResponseEntity<TaskResponse> getTask(@PathVariable final UUID id) {
        final Task task = taskService.getTask(id);
        return ResponseEntity.ok(taskMapper.dtoToResponse(task));
    }

    @GetMapping
    public ResponseEntity<PageDto<TaskResponse>> getTasks(
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final PageDto<Task> taskPage = taskService.getTasks(page);
        return ResponseEntity.ok(taskPage.map(taskMapper::dtoToResponse));
    }

    @GetMapping(GET_TASK_BY_TASK_STATUS_SUB_PATH)
    public ResponseEntity<PageDto<TaskResponse>> getTasksByTaskStatus(
            @PathVariable final TaskStatusEnum status,
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final PageDto<Task> taskPage = taskService.getTasksByTaskStatus(status, page);
        return ResponseEntity.ok(taskPage.map(taskMapper::dtoToResponse));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid final TaskRequest request) {
        final Task task = taskMapper.requestToDto(request);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path(GET_TASK_SUB_PATH)
                .buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(
                location
        ).body(
                taskMapper.dtoToResponse(
                        taskService.createTask(task)
                )
        );
    }

    @DeleteMapping(DEL_TASK_SUB_PATH)
    public ResponseEntity<Void> deleteTask(@PathVariable final UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(PUT_MARK_AS_FINISHED_SUB_PATH)
    public ResponseEntity<Void> updateTaskAsFinished(@PathVariable final UUID id) {
        taskService.updateTaskAsFinished(id);
        return ResponseEntity.noContent().build();
    }
}
