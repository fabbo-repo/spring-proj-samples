package com.prueba.homeworkapp.modules.task.infrastructure.input.rest.data.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prueba.homeworkapp.commons.config.TimeZoneConfig;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskRequest {
        @NotBlank
        @Size(min = 3, max = 30)
        private String title;

        @NotNull
        @Size(max = 500)
        private String description;

        @NotNull
        @JsonFormat(pattern = TimeZoneConfig.DATE_ISO_FORMAT)
        private LocalDateTime estimatedDoneAt;

        @JsonFormat(pattern = TimeZoneConfig.DATE_ISO_FORMAT)
        private LocalDateTime finishedAt;

        private boolean finished;

        @NotNull
        private TaskStatusEnum taskStatus;
}
