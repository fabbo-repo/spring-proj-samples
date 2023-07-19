package com.prueba.homeworkapp.modules.task.domain.models.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDto {
	private UUID id;

	private String title;

	private String description;

	private LocalDateTime estimatedDoneAt;

	private LocalDateTime finishedAt;

	private boolean finished;

	private TaskStatusEnum taskStatusEnum;
}
