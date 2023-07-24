package com.prueba.homeworkapp.modules.task.domain.models.entities;

import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = Task.TABLE_NAME)
public class Task {
    public final static String TABLE_NAME = "task";
    public final static String SORT_FIELD = "createdAt";
    public final static String ID_COL = "id";
    private final static String TITLE_COL = "title";
    private final static String DESCRIPTION_COL = "description";
    private final static String ESTIMATED_DONE_AT_COL = "estimated_done_date";
    private final static String FINISHED_AT_COL = "finished_date";
    private final static String FINISHED_COL = "is_finished";
    private final static String TASK_STATUS_COL = "task_status";
    public final static String CREATED_AT_COL = "created_at";
    private final static String CREATED_BY_COL = "created_by";
    private final static String UPDATED_AT_COL = "updated_at";
    private final static String UPDATED_BY_COL = "updated_by";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = ID_COL)
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
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum taskStatus;

    @CreatedDate
    @Column(name = CREATED_AT_COL, updatable = false)
    private LocalDateTime createdAt; // audit field

    @CreatedBy
    @Column(name = CREATED_BY_COL, updatable = false)
    private String createdBy; // audit field

    @LastModifiedDate
    @Column(name = UPDATED_AT_COL, insertable = false)
    private LocalDateTime updatedAt; // audit field

    @LastModifiedBy
    @Column(name = UPDATED_BY_COL, insertable = false)
    private String updatedBy; // audit field
}
