package com.prueba.homeworkapp.modules.task.application.rest_controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.homeworkapp.core.config.SecurityConfig;
import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.sevices.ITaskService;
import com.prueba.homeworkapp.modules.task.domain.models.dtos.TaskDto;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/task")
@SecurityRequirement(name = SecurityConfig.SECURITY_CONFIG_NAME)
public class TaskController {
	
	private final ITaskService taskService;
	
	public TaskController(ITaskService taskService) {
		this.taskService = taskService;
	}
	
	@PostMapping
	public Task createTask(@RequestBody TaskDto taskDto) {
		return this.taskService.createTask(taskDto);
	}

	@GetMapping("/{id}")
	public Task findById(@PathVariable("id") Long id) {
		return this.taskService.findById(id);
	}
	
	@GetMapping
	public List<Task> findAll() {
		return this.taskService.findAll();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		this.taskService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/status/{status}")
	public List<Task> findAllByTaskStatus(@PathVariable("status") TaskStatusEnum status) {
		return this.taskService.findAllByTaskStatus(status);
	}
	
	@PatchMapping("/mark-as-finished/{id}")
	public ResponseEntity<Void> markAsFinished(@PathVariable("id") Long id) {
		this.taskService.updateTaskAsFinished(id);
		return ResponseEntity.noContent().build();
	}
}
