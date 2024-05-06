package com.prueba.homeworkapp.modules.task.infrastructure.input.rest.data.mappers;

import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.infrastructure.input.rest.data.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.infrastructure.input.rest.data.responses.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskRestMapper {
    TaskRestMapper INSTANCE = Mappers.getMapper(TaskRestMapper.class);

    TaskResponse taskToTaskResponse(final Task task);

    @Mapping(target = "id", ignore = true)
    Task taskRequestToTask(final TaskRequest request);
}
