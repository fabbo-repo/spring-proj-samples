package com.mercadonatest.eanapi.core.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        String message,
        List<ParamErrorResponse> params
) {
}
