package com.prueba.homeworkapp.modules.task.domain.models.mappers;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.dtos.TaskDto;

@Component
public class TaskMapper implements IMapper<TaskDto, Task>{

	@Override
	public Task map(TaskDto in) {
		Task task = new Task();
		task.setTitle(in.getTitle());
		task.setDescription(in.getDescription());
		task.setEta(in.getEta());
		task.setCreatedDate(LocalDateTime.now());
		task.setFinished(false);
		task.setTaskStatusEnum(TaskStatusEnum.ON_TIME);
		return task;
	}
}
