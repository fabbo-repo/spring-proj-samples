package com.prueba.homeworkapp.modules.task.domain.props;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UpdateTaskProps {
    private UUID id;

    private boolean finished;
}
