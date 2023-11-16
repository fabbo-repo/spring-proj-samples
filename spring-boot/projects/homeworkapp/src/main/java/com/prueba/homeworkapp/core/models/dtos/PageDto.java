package com.prueba.homeworkapp.core.models.dtos;

import java.util.List;
import java.util.function.Function;

public record PageDto<T>(
        long count,
        String next,
        String previous,
        List<T> results
) {
    public <U> PageDto<U> map(Function<T, U> converter) {
        return new PageDto<>(
                count,
                next,
                previous,
                results.stream().map(converter).toList()
        );
    }
}