package com.prueba.homeworkapp.modules.task.models.mappers;

import com.prueba.homeworkapp.modules.task.models.dtos.Task;
import com.prueba.homeworkapp.modules.task.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.models.responses.TaskResponse;
import com.prueba.homeworkapp.modules.task.models.entities.TaskJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "id", ignore = true)
    Task requestToDto(final TaskRequest taskRequest);

    TaskResponse dtoToResponse(final Task task);

    TaskJpaEntity dtoToEntity(final Task task);

    Task entityToDto(final TaskJpaEntity taskJpaEntity);
}
