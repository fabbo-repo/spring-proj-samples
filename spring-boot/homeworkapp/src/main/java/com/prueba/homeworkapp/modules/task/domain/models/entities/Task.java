package com.prueba.homeworkapp.modules.task.domain.models.entities;

import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = Task.TABLE_NAME)
public class Task {
    public final static String TABLE_NAME = "task";
    private final static String TITLE_COL = "title";
    private final static String DESCRIPTION_COL = "description";
    private final static String ESTIMATED_DONE_AT_COL = "estimated_done_date";
    private final static String FINISHED_AT_COL = "finished_date";
    private final static String FINISHED_COL = "is_finished";
    private final static String TASK_STATUS_COL = "task_status";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(name = TITLE_COL)
    private String title;

    @NotNull
    @Size(max = 500)
    @Column(name = DESCRIPTION_COL)
    private String description;

    @NotNull
    @Column(name = ESTIMATED_DONE_AT_COL)
    private LocalDateTime estimatedDoneAt;

    @Column(name = FINISHED_AT_COL)
    private LocalDateTime finishedAt;

    @Column(name = FINISHED_COL)
    private boolean finished;

    @NotNull
    @Column(name = TASK_STATUS_COL)
    private TaskStatusEnum taskStatusEnum;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;
}
