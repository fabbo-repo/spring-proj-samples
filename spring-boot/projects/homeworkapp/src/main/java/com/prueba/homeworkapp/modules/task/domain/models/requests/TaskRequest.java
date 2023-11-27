package com.prueba.homeworkapp.modules.task.domain.models.requests;

import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
public record TaskRequest(
        @NotBlank
        @Size(min = 3, max = 30)
        String title,
        @NotNull
        @Size(max = 500)
        String description,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime estimatedDoneAt,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime finishedAt,
        boolean finished,
        @NotNull
        TaskStatusEnum taskStatus
) {
}
