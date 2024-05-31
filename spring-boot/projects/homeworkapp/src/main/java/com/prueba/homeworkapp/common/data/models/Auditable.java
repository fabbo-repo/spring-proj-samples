package com.prueba.homeworkapp.common.data.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Auditable {
    protected LocalDateTime createdAt;

    protected String createdBy;


    private LocalDateTime updatedAt;

    private String updatedBy;
}