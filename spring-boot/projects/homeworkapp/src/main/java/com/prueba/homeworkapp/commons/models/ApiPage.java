package com.prueba.homeworkapp.commons.models;

import java.util.List;
import java.util.function.Function;

public record ApiPage<T>(
        long count,
        String next,
        String previous,
        List<T> results
) {
    public <U> ApiPage<U> map(Function<T, U> converter) {
        return new ApiPage<>(
                count,
                next,
                previous,
                results.stream().map(converter).toList()
        );
    }
}