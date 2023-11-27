package com.spike.mongodb.models.entities;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
public class AuditableCollectionEntity {

    public static final String CREATED_BY_FIELD_NAME = "createdBy";
    public static final String CREATED_DATE_FIELD_NAME = "createdDate";
    public static final String LAST_MODIFIED_BY_FIELD_NAME = "lastModifiedBy";
    public static final String LAST_MODIFIED_DATE_FIELD_NAME = "lastModifiedDate";

    @CreatedBy
    @Field(CREATED_BY_FIELD_NAME)
    private String createdBy;

    @CreatedDate
    @Field(CREATED_DATE_FIELD_NAME)
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Field(LAST_MODIFIED_BY_FIELD_NAME)
    private String lastModifiedBy;

    @LastModifiedDate
    @Field(LAST_MODIFIED_DATE_FIELD_NAME)
    private LocalDateTime lastModifiedDate;
}
