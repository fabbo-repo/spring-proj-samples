package com.prueba.homeworkapp.modules.task.infrastructure.repositories.jpa;

import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskJpaRepository extends JpaRepository<Task, Long>{
	List<Task> findAllByTaskStatus(final TaskStatusEnum taskStatusEnum);
}
