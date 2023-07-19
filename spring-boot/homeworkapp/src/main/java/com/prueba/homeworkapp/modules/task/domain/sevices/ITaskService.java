package com.prueba.homeworkapp.modules.task.domain.sevices;

import java.util.List;
import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskStatus;
import com.prueba.homeworkapp.modules.task.domain.models.dtos.TaskInDTO;

public interface ITaskService {

	Task createTask(TaskInDTO taskInDTO);

	Task findById(Long id);
	
	List<Task> findAll();

	List<Task> findAllByTaskStatus(TaskStatus status);
	
	void updateTaskAsFinished(Long id);

	void deleteById(Long id);
}