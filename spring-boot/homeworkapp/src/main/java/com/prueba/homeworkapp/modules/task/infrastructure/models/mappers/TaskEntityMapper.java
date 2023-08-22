package com.prueba.homeworkapp.modules.task.infrastructure.models.mappers;

import com.prueba.homeworkapp.modules.task.domain.models.dtos.Task;
import com.prueba.homeworkapp.modules.task.infrastructure.models.entities.TaskJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskEntityMapper {
    TaskEntityMapper INSTANCE = Mappers.getMapper(TaskEntityMapper.class);

    TaskJpaEntity dtoToEntity(final Task task);

    Task entityToDto(final TaskJpaEntity taskJpaEntity);
}
