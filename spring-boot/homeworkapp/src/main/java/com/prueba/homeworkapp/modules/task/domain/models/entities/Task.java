package com.prueba.homeworkapp.modules.task.domain.models.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private String description;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime eta;

	private boolean finished;
	
	private TaskStatus taskStatus;
}
