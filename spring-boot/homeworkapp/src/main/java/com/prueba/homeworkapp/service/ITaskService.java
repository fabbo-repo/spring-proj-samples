package com.prueba.homeworkapp.service;

import java.util.List;
import com.prueba.homeworkapp.persistence.entity.Task;
import com.prueba.homeworkapp.persistence.entity.TaskStatus;
import com.prueba.homeworkapp.service.dto.TaskInDTO;

public interface ITaskService {

	Task createTask(TaskInDTO taskInDTO);

	Task findById(Long id);
	
	List<Task> findAll();

	List<Task> findAllByTaskStatus(TaskStatus status);
	
	void updateTaskAsFinished(Long id);

	void deleteById(Long id);
}