package com.prueba.homeworkapp.modules.task.infrastructure.input.rest.controllers;

import com.prueba.homeworkapp.common.data.models.ApiPage;
import com.prueba.homeworkapp.common.data.props.ApiPageProps;
import com.prueba.homeworkapp.modules.task.application.usecases.CreateTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.DeleteTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.FilterTasksUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.GetTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.GetTasksUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.UpdateTaskUseCase;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.domain.props.CreateTaskProps;
import com.prueba.homeworkapp.modules.task.domain.props.FilerTasksProps;
import com.prueba.homeworkapp.modules.task.domain.props.UpdateTaskProps;
import com.prueba.homeworkapp.modules.task.infrastructure.input.rest.mappers.TaskRestMapper;
import com.prueba.homeworkapp.modules.task.infrastructure.input.rest.requests.TaskRestRequest;
import com.prueba.homeworkapp.modules.task.infrastructure.input.rest.responses.TaskRestResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
@RequestMapping(path = TaskRestController.CONTROLLER_PATH)
@Validated
@Tag(name = TaskRestController.SWAGGER_TAG)
@RequiredArgsConstructor
public class TaskRestController {
    public static final String SWAGGER_TAG = "Task API";

    @Value("${api.pagination.page-size}")
    private int pageSize;

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
    public ResponseEntity<TaskRestResponse> getTask(@PathVariable final UUID id) {
        final Task task = getTaskUseCase.getTask(id);
        return ResponseEntity.ok(
                TASK_REST_MAPPER.taskToTaskResponse(task)
        );
    }

    @GetMapping
    public ResponseEntity<ApiPage<TaskRestResponse>> getTasks(
            @RequestParam(defaultValue = "0") @Min(0) final Integer pageNum
    ) {
        final ApiPage<Task> taskPage = getTasksUseCase.getTasks(
            new ApiPageProps(
                    pageNum,
                    pageSize
            )
        );
        return ResponseEntity.ok(
                taskPage.map(TASK_REST_MAPPER::taskToTaskResponse)
        );
    }

    @GetMapping(GET_TASK_BY_TASK_STATUS_SUB_PATH)
    public ResponseEntity<ApiPage<TaskRestResponse>> getTasksByTaskStatus(
            @PathVariable final TaskStatusEnum status,
            @RequestParam(defaultValue = "0") @Min(0) final Integer pageNum
    ) {
        final ApiPage<Task> taskPage = filterTasksUseCase
                .filterTasks(
                        new FilerTasksProps(
                                pageNum,
                                pageSize,
                                status
                        )
                );
        return ResponseEntity.ok(
                taskPage.map(TASK_REST_MAPPER::taskToTaskResponse)
        );
    }

    @PostMapping
    public ResponseEntity<TaskRestResponse> createTask(
            @RequestBody @Valid final TaskRestRequest request
    ) {
        final Task newTask = createTaskUseCase.createTask(
                new CreateTaskProps(
                        request.getTitle(),
                        request.getDescription(),
                        request.getEstimatedDoneAt(),
                        request.getFinishedAt(),
                        request.getFinished()
                )
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
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
        final UpdateTaskProps updateTaskDto = new UpdateTaskProps(
                id,
                true
        );
        updateTaskUseCase.updateTask(updateTaskDto);
        return ResponseEntity.noContent().build();
    }
}
