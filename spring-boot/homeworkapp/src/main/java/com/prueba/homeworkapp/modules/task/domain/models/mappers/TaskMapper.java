package com.prueba.homeworkapp.modules.task.domain.models.mappers;

import com.prueba.homeworkapp.modules.task.domain.models.dtos.TaskDto;
import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "id", ignore = true)
    TaskDto requestToDto(final TaskRequest taskRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Task requestToEntity(final TaskRequest taskRequest);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Task dtoToEntity(final TaskDto taskDto);

    TaskResponse entityToResponse(final Task task);
}
