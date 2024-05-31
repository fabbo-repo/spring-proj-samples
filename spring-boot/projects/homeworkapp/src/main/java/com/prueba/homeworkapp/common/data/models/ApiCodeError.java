package com.prueba.homeworkapp.common.data.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiCodeError(
        Integer errorCode,
        String message,
        List<ApiParamError> params
) {
}