package com.prueba.homeworkapp.modules.task.application.controllers;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;
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
@Tag(name = TaskController.SWAGGER_TAG)
@RequiredArgsConstructor
public class TaskController {
    public static final String SWAGGER_TAG = "Task API";

    @SuppressWarnings("squid:S1075")
    public static final String CONTROLLER_PATH = "/task";
    @SuppressWarnings("squid:S1075")
    public static final String GET_TASK_SUB_PATH = "/{id}";
    @SuppressWarnings("squid:S1075")
    public static final String GET_TASK_BY_TASK_STATUS_SUB_PATH = "/status/{status}";
    @SuppressWarnings("squid:S1075")
    public static final String DEL_TASK_SUB_PATH = "/{id}";
    @SuppressWarnings("squid:S1075")
    public static final String PUT_MARK_AS_FINISHED_SUB_PATH = "/mark-as-finished/{id}";

    private final TaskService taskService;

    @GetMapping(GET_TASK_SUB_PATH)
    public ResponseEntity<TaskResponse> getTask(@PathVariable final UUID id) {
        final TaskResponse task = taskService.getTask(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<PageDto<TaskResponse>> getTasks(
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final PageDto<TaskResponse> taskPage = taskService.getTasks(page);
        return ResponseEntity.ok(taskPage);
    }

    @GetMapping(GET_TASK_BY_TASK_STATUS_SUB_PATH)
    public ResponseEntity<PageDto<TaskResponse>> getTasksByTaskStatus(
            @PathVariable final TaskStatusEnum status,
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final PageDto<TaskResponse> taskPage = taskService.getTasksByTaskStatus(status, page);
        return ResponseEntity.ok(taskPage);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid final TaskRequest request) {
        final TaskResponse response = taskService.createTask(request);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path(GET_TASK_SUB_PATH)
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity
                .created(location)
                .body(response);
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
