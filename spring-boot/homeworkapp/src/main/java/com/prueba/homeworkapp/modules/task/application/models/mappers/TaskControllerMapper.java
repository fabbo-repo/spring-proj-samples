package com.prueba.homeworkapp.modules.task.application.models.mappers;

import com.prueba.homeworkapp.modules.task.domain.models.dtos.Task;
import com.prueba.homeworkapp.modules.task.application.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.application.models.responses.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskControllerMapper {
    TaskControllerMapper INSTANCE = Mappers.getMapper(TaskControllerMapper.class);

    @Mapping(target = "id", ignore = true)
    Task requestToDto(final TaskRequest taskRequest);

    TaskResponse dtoToResponse(final Task task);
}
