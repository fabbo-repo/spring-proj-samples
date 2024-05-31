package com.prueba.homeworkapp.common.data.models;

import java.util.List;
import java.util.function.Function;

public record ApiPage<T>(
        long totalElements,
        int pageSize,
        long pageIndex,
        long firstPage,
        long lastPage,
        List<T> results
) {
    public <U> ApiPage<U> map(Function<T, U> converter) {
        return new ApiPage<>(
                totalElements,
                pageSize,
                pageIndex,
                firstPage,
                lastPage,
                results.stream().map(converter).toList()
        );
    }
}
