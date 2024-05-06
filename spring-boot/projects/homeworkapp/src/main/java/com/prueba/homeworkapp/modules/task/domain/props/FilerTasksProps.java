package com.prueba.homeworkapp.modules.task.domain.props;

import com.prueba.homeworkapp.common.data.props.ApiPageProps;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FilerTasksProps extends ApiPageProps {
    private TaskStatusEnum taskStatus;

    public FilerTasksProps(int pageNum, int pageSize, TaskStatusEnum taskStatus) {
        super(pageNum, pageSize);
        this.taskStatus = taskStatus;
    }
}
