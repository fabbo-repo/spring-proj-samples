package com.prueba.homeworkapp.modules.task.domain.sevices;

import java.util.List;
import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.dtos.TaskDto;

public interface ITaskService {

	Task createTask(TaskDto taskDto);

	Task findById(Long id);
	
	List<Task> findAll();

	List<Task> findAllByTaskStatus(TaskStatusEnum status);
	
	void updateTaskAsFinished(Long id);

	void deleteById(Long id);
}