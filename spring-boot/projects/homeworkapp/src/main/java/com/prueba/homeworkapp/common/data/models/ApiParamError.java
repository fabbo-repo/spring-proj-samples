package com.prueba.homeworkapp.common.data.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.io.Serializable;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiParamError(
        String field,
        String message
) implements Serializable {
}