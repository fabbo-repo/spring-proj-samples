package com.prueba.homeworkapp.core.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.io.Serializable;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ParamErrorResponse(
        String field,
        String message
) implements Serializable {
}