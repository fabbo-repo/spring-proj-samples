package com.prueba.homeworkapp.modules.task.domain.models.mappers;

import com.prueba.homeworkapp.modules.task.domain.models.dtos.Task;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;
import com.prueba.homeworkapp.modules.task.domain.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "id", ignore = true)
    TaskJpaEntity requestToEntity(final TaskRequest taskRequest);

    TaskResponse dtoToResponse(final Task task);

    TaskResponse entityToResponse(final TaskJpaEntity taskJpaEntity);
}
