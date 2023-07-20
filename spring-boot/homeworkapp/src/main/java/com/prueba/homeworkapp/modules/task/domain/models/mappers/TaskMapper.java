package com.prueba.homeworkapp.modules.task.domain.models.mappers;

import com.prueba.homeworkapp.modules.task.domain.models.dtos.TaskDto;
import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDto requestToDto(final TaskRequest taskRequest);

    Task requestToEntity(final TaskRequest taskRequest);

    Task dtoToEntity(final TaskDto taskDto);

    TaskResponse entityToResponse(final Task task);
}
