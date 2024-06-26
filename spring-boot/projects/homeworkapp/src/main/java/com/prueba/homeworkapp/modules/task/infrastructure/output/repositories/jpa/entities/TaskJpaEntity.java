package com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.entities;

import com.prueba.homeworkapp.common.data.entities.AuditableJpaEntity;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TaskJpaEntity.TABLE_NAME)
public class TaskJpaEntity extends AuditableJpaEntity {
    public static final String TABLE_NAME = "task";

    public static final String SORT_FIELD = "createdAt";
    public static final String ID_COL = "id";
    private static final String TITLE_COL = "title";
    private static final String DESCRIPTION_COL = "description";
    private static final String ESTIMATED_DONE_AT_COL = "estimated_done_date";
    private static final String FINISHED_AT_COL = "finished_date";
    private static final String FINISHED_COL = "is_finished";
    private static final String TASK_STATUS_COL = "task_status";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = ID_COL)
    private UUID id;

    @Column(name = TITLE_COL, nullable = false)
    private String title;

    @Column(name = DESCRIPTION_COL, nullable = false)
    private String description;

    @Column(name = ESTIMATED_DONE_AT_COL, nullable = false)
    private LocalDateTime estimatedDoneAt;

    @Column(name = FINISHED_AT_COL)
    private LocalDateTime finishedAt;

    @Column(name = FINISHED_COL)
    private boolean finished;

    @Column(name = TASK_STATUS_COL, nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum taskStatus;
}
