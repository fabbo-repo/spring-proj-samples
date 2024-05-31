package com.prueba.homeworkapp.modules.task.domain.props;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CreateTaskProps {
    private String title;

    private String description;

    private LocalDateTime estimatedDoneAt;

    private LocalDateTime finishedAt;

    private boolean finished;
}
