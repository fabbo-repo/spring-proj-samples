package com.prueba.homeworkapp.common.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableJpaEntity {
    public static final String CREATED_AT_COL = "created_at";
    private static final String CREATED_BY_COL = "created_by";
    private static final String UPDATED_AT_COL = "updated_at";
    private static final String UPDATED_BY_COL = "updated_by";

    @CreatedDate
    @Column(name = CREATED_AT_COL)
    protected LocalDateTime createdAt;

    @CreatedBy
    @Column(name = CREATED_BY_COL)
    protected String createdBy;


    @LastModifiedDate
    @Column(name = UPDATED_AT_COL)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = UPDATED_BY_COL)
    private String updatedBy;
}