package com.prueba.homeworkapp.core.domain.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse<T>(
        int count,
        String next,
        String previous,
        List<T> results
) {
}