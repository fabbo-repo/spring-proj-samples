package com.prueba.homeworkapp.modules.task.domain.props;

import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class FilerTasksProps {
    private TaskStatusEnum taskStatus;

    private int pageNum;

    private int pageSize;
}
