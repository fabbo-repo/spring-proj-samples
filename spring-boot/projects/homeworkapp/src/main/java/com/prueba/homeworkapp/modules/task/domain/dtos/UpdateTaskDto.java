package com.prueba.homeworkapp.modules.task.domain.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class UpdateTaskDto {
    private UUID id;

    private boolean finished;
}
