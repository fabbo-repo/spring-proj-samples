package com.prueba.homeworkapp.modules.task.domain.models.requests;

import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TaskRequest(
        @NotBlank
        @Size(min = 3, max = 30)
        String title,
        @NotNull
        @Size(max = 500)
        String description,
        @NotNull
        LocalDateTime estimatedDoneAt,
        LocalDateTime finishedAt,
        boolean finished,
        @NotNull
        TaskStatusEnum taskStatusEnum
) {
}
