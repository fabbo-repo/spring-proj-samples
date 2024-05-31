package com.prueba.homeworkapp.modules.task.domain.models;

import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Task {
    private UUID id;

    private String title;

    private String description;

    private LocalDateTime estimatedDoneAt;

    private LocalDateTime finishedAt;

    private boolean finished = false;

    private TaskStatusEnum taskStatus = TaskStatusEnum.IN_PROGRESS;

    public Task() {
    }

    public Task(
            final UUID id,
            final String title,
            final String description,
            final LocalDateTime estimatedDoneAt
    ) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setEstimatedDoneAt(estimatedDoneAt);
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public void setTitle(@NonNull final String title) {
        this.title = title;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEstimatedDoneAt(final LocalDateTime estimatedDoneAt) {
        this.estimatedDoneAt = estimatedDoneAt;
    }

    public void setFinishedAt(final LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public void setFinished(final boolean finished) {
        this.finished = finished;
    }

    public void setTaskStatus(@NonNull final TaskStatusEnum taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return finished == task.finished && Objects.equals(id, task.id) &&
               Objects.equals(title, task.title) && Objects.equals(description, task.description) &&
               Objects.equals(estimatedDoneAt, task.estimatedDoneAt) &&
               Objects.equals(finishedAt, task.finishedAt) && taskStatus == task.taskStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, estimatedDoneAt, finishedAt, finished, taskStatus);
    }
}
