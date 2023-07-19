package com.prueba.homeworkapp.modules.task.domain.sevices;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.prueba.homeworkapp.core.domain.exceptions.ToDoException;
import com.prueba.homeworkapp.modules.task.domain.models.mappers.TaskInDTOToTask;
import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskStatus;
import com.prueba.homeworkapp.modules.task.infrastructure.jpa_repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.domain.models.dtos.TaskInDTO;

@Service
public class TaskService implements ITaskService {
	
	private final TaskRepository taskRepository;
	
	private final TaskInDTOToTask mapper;
	
	/**
	 * Dependency injection based on constructor.
	 * This solution avoids AutoWired tag in repository field.
	 * 
	 * @param repository
	 */
	public TaskService(TaskRepository repository, TaskInDTOToTask mapper) {
		this.taskRepository = repository;
		this.mapper = mapper;
	}
	
	@Override
	public Task createTask(TaskInDTO taskInDTO) {
		Task task = mapper.map(taskInDTO);
		return this.taskRepository.save(task);
	}
	
	@Override
	public Task findById(Long id) {
		Optional<Task> optionalTask = this.taskRepository.findById(id);
		if (optionalTask.isEmpty()) {
			throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);
		}
		return optionalTask.get();
	}
	
	@Override
	public List<Task> findAll() {
		return this.taskRepository.findAll();
	}
	
	@Override
	public List<Task> findAllByTaskStatus(TaskStatus status) {
		return this.taskRepository.findAllByTaskStatus(status);
	}

	@Override
	@Transactional
	public void updateTaskAsFinished(Long id) {
		Optional<Task> optionalTask = this.taskRepository.findById(id);
		if (optionalTask.isEmpty()) {
			throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);
		}
		this.taskRepository.markTaskAsFinished(id);
	}

	@Override
	public void deleteById(Long id) {
		Optional<Task> optionalTask = this.taskRepository.findById(id);
		if (optionalTask.isEmpty()) {
			throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);
		}
		this.taskRepository.deleteById(id);
	}
}
