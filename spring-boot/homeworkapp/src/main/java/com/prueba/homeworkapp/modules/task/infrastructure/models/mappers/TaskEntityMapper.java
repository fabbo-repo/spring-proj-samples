package com.prueba.homeworkapp.modules.task.infrastructure.models.mappers;

import com.prueba.homeworkapp.modules.task.domain.models.dtos.Task;
import com.prueba.homeworkapp.modules.task.infrastructure.models.entities.TaskJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskEntityMapper {
    TaskEntityMapper INSTANCE = Mappers.getMapper(TaskEntityMapper.class);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    TaskJpaEntity dtoToEntity(final Task task);

    Task entityToDto(final TaskJpaEntity taskJpaEntity);
}
