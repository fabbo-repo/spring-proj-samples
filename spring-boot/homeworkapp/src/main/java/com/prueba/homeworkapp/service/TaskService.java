package com.prueba.homeworkapp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.prueba.homeworkapp.exceptions.ToDoException;
import com.prueba.homeworkapp.mapper.TaskInDTOToTask;
import com.prueba.homeworkapp.persistence.entity.Task;
import com.prueba.homeworkapp.persistence.entity.TaskStatus;
import com.prueba.homeworkapp.persistence.repository.TaskRepo;
import com.prueba.homeworkapp.service.dto.TaskInDTO;

@Service
public class TaskService implements ITaskService {
	
	private final TaskRepo taskRepo;
	
	private final TaskInDTOToTask mapper;
	
	/**
	 * Dependency injection based on constructor.
	 * This solution avoids AutoWired tag in repository field.
	 * 
	 * @param repository
	 */
	public TaskService(TaskRepo repository, TaskInDTOToTask mapper) {
		this.taskRepo = repository;
		this.mapper = mapper;
	}
	
	@Override
	public Task createTask(TaskInDTO taskInDTO) {
		Task task = mapper.map(taskInDTO);
		return this.taskRepo.save(task);
	}
	
	@Override
	public Task findById(Long id) {
		Optional<Task> optionalTask = this.taskRepo.findById(id);
		if (optionalTask.isEmpty()) {
			throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);
		}
		return optionalTask.get();
	}
	
	@Override
	public List<Task> findAll() {
		return this.taskRepo.findAll();
	}
	
	@Override
	public List<Task> findAllByTaskStatus(TaskStatus status) {
		return this.taskRepo.findAllByTaskStatus(status);
	}

	@Override
	@Transactional
	public void updateTaskAsFinished(Long id) {
		Optional<Task> optionalTask = this.taskRepo.findById(id);
		if (optionalTask.isEmpty()) {
			throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);
		}
		this.taskRepo.markTaskAsFinished(id);
	}

	@Override
	public void deleteById(Long id) {
		Optional<Task> optionalTask = this.taskRepo.findById(id);
		if (optionalTask.isEmpty()) {
			throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);
		}
		this.taskRepo.deleteById(id);
	}
}
