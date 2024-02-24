package com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.mappers;

import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskJpaMapper {
    TaskJpaMapper INSTANCE = Mappers.getMapper(TaskJpaMapper.class);

    TaskJpaEntity taskToTaskEntity(final Task task);

    Task taskEntityToTask(final TaskJpaEntity taskJpaEntity);
}
