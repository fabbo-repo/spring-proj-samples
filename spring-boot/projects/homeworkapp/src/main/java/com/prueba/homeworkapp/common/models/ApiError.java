package com.prueba.homeworkapp.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        String message,
        List<ParamError> params
) {
}