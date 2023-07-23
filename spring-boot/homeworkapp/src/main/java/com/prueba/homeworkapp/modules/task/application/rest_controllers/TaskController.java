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

    @PostMapping
    public TaskResponse createTask(@RequestBody @Valid final TaskRequest request) {
        return this.taskService.createTask(request);
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable final UUID id) {
        return this.taskService.getTask(id);
    }

    @GetMapping
    public PageResponse<TaskResponse> getTasks(
            @RequestParam @Min(0) final int page
    ) {
        return this.taskService.getTasks(page);
    }

    @GetMapping("/status/{status}")
    public PageResponse<TaskResponse> getTasksByTaskStatus(
            @PathVariable final TaskStatusEnum status,
            @RequestParam @Min(0) final int page
    ) {
        return this.taskService.getTasksByTaskStatus(status, page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable final UUID id) {
        this.taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/mark-as-finished/{id}")
    public ResponseEntity<Void> updateTaskAsFinished(@PathVariable final UUID id) {
        this.taskService.updateTaskAsFinished(id);
        return ResponseEntity.noContent().build();
    }
}