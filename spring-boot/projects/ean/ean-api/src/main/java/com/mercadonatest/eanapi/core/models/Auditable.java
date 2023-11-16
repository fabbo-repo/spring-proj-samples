package com.mercadonatest.eanapi.core.models;

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
public abstract class Auditable<U> {
    public final static String CREATED_AT_COL = "created_at";
    private final static String CREATED_BY_COL = "created_by";
    private final static String UPDATED_AT_COL = "updated_at";
    private final static String UPDATED_BY_COL = "updated_by";

    @CreatedDate
    @Column(name = CREATED_AT_COL)
    protected LocalDateTime createdAt;

    @CreatedBy
    @Column(name = CREATED_BY_COL)
    protected U createdBy;

    @LastModifiedDate
    @Column(name = UPDATED_AT_COL)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = UPDATED_BY_COL)
    private U updatedBy;
}
