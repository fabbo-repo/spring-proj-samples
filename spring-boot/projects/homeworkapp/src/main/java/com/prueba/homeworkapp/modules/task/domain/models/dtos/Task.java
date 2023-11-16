package com.prueba.homeworkapp.modules.task.domain.models.dtos;

import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Task {
	private UUID id;

	private String title;

	private String description;

	private LocalDateTime estimatedDoneAt;

	private LocalDateTime finishedAt;

	private boolean finished;

	private TaskStatusEnum taskStatus;
}
