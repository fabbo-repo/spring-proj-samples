package com.prueba.homeworkapp.modules.task.domain.props;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class FilerTasksProps {
    private UUID id;

    private boolean finished;
}
