package com.prueba.homeworkapp.modules.task.application.rest_controllers;

import com.prueba.homeworkapp.core.domain.responses.PageResponse;
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

import java.util.UUID;

@RestController
@RequestMapping(path = "/task")
@Validated
@Tag(name = "Task API")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable final UUID id) {
        return taskService.getTask(id);
    }

    @GetMapping
    public PageResponse<TaskResponse> getTasks(
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        return taskService.getTasks(page);
    }

    @GetMapping("/status/{status}")
    public PageResponse<TaskResponse> getTasksByTaskStatus(
            @PathVariable final TaskStatusEnum status,
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        return taskService.getTasksByTaskStatus(status, page);
    }

    @PostMapping
    public TaskResponse createTask(@RequestBody @Valid final TaskRequest request) {
        return taskService.createTask(request);
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
