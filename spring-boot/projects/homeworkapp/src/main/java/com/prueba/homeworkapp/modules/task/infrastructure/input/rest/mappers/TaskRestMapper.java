package com.prueba.homeworkapp.modules.task.infrastructure.input.rest.mappers;

import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.infrastructure.input.rest.responses.TaskRestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskRestMapper {
    TaskRestMapper INSTANCE = Mappers.getMapper(TaskRestMapper.class);

    TaskRestResponse taskToTaskResponse(final Task task);
}
