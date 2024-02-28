package com.prueba.homeworkapp.modules.task.infrastructure.input.rest;

import com.prueba.homeworkapp.common.models.ApiPage;
import com.prueba.homeworkapp.modules.task.application.usecases.CreateTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.DeleteTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.FilterTasksUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.GetTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.GetTasksUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.UpdateTaskUseCase;
import com.prueba.homeworkapp.modules.task.domain.dtos.UpdateTaskDto;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.infrastructure.input.rest.data.mappers.TaskRestMapper;
import com.prueba.homeworkapp.modules.task.infrastructure.input.rest.data.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.infrastructure.input.rest.data.responses.TaskResponse;
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
@RequestMapping(path = TaskRestController.CONTROLLER_PATH)
@Validated
@Tag(name = TaskRestController.SWAGGER_TAG)
@RequiredArgsConstructor
public class TaskRestController {
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

    private static final TaskRestMapper TASK_REST_MAPPER = TaskRestMapper.INSTANCE;

    private final GetTaskUseCase getTaskUseCase;

    private final GetTasksUseCase getTasksUseCase;

    private final FilterTasksUseCase filterTasksUseCase;

    private final CreateTaskUseCase createTaskUseCase;

    private final UpdateTaskUseCase updateTaskUseCase;

    private final DeleteTaskUseCase deleteTaskUseCase;

    @GetMapping(GET_TASK_SUB_PATH)
    public ResponseEntity<TaskResponse> getTask(@PathVariable final UUID id) {
        final Task task = getTaskUseCase.getTask(id);
        return ResponseEntity.ok(
                TASK_REST_MAPPER.taskToTaskResponse(task)
        );
    }

    @GetMapping
    public ResponseEntity<ApiPage<TaskResponse>> getTasks(
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final ApiPage<Task> taskPage = getTasksUseCase.getTasks(page);
        return ResponseEntity.ok(
                taskPage.map(TASK_REST_MAPPER::taskToTaskResponse)
        );
    }

    @GetMapping(GET_TASK_BY_TASK_STATUS_SUB_PATH)
    public ResponseEntity<ApiPage<TaskResponse>> getTasksByTaskStatus(
            @PathVariable final TaskStatusEnum status,
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final ApiPage<Task> taskPage = filterTasksUseCase
                .filterTasks(status, page);
        return ResponseEntity.ok(
                taskPage.map(TASK_REST_MAPPER::taskToTaskResponse)
        );
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid final TaskRequest request) {
        final Task newTask = createTaskUseCase.createTask(
                TASK_REST_MAPPER.taskRequestToTask(request)
        );
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path(GET_TASK_SUB_PATH)
                .buildAndExpand(newTask.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(
                        TASK_REST_MAPPER.taskToTaskResponse(newTask)
                );
    }

    @DeleteMapping(DEL_TASK_SUB_PATH)
    public ResponseEntity<Void> deleteTask(@PathVariable final UUID id) {
        deleteTaskUseCase.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(PUT_MARK_AS_FINISHED_SUB_PATH)
    public ResponseEntity<Void> updateTaskAsFinished(@PathVariable final UUID id) {
        final UpdateTaskDto updateTaskDto = new UpdateTaskDto();
        updateTaskDto.setId(id);
        updateTaskDto.setFinished(true);
        updateTaskUseCase.updateTask(updateTaskDto);
        return ResponseEntity.noContent().build();
    }
}
