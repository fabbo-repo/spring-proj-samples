package com.prueba.homeworkapp.modules.task.models.responses;

import com.prueba.homeworkapp.modules.task.models.enums.TaskStatusEnum;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TaskResponse(
        UUID id,
        String title,
        String description,
        LocalDateTime estimatedDoneAt,
        LocalDateTime finishedAt,
        boolean finished,
        TaskStatusEnum taskStatus
) {
}