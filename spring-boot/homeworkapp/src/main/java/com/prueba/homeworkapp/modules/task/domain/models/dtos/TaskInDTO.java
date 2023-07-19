package com.prueba.homeworkapp.modules.task.domain.models.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskInDTO {
	
	private String title;
	
	private String description;
	
	private LocalDateTime eta;
}
