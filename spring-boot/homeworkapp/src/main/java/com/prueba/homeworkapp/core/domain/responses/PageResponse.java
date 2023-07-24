package com.prueba.homeworkapp.core.domain.responses;

import java.util.List;

public record PageResponse<T>(
        long count,
        String next,
        String previous,
        List<T> results
) {
}