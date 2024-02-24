package com.prueba.homeworkapp.modules.task.infrastructure.input.rest.data.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prueba.homeworkapp.commons.config.TimeZoneConfig;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskResponse {
    private UUID id;

    private String title;

    private String description;

    @JsonFormat(pattern = TimeZoneConfig.DATE_ISO_FORMAT)
    private LocalDateTime estimatedDoneAt;

    @JsonFormat(pattern = TimeZoneConfig.DATE_ISO_FORMAT)
    private LocalDateTime finishedAt;

    private Boolean finished;

    private TaskStatusEnum taskStatus;
}
