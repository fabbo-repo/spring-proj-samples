package com.prueba.homeworkapp.common.data.mapper;

import com.prueba.homeworkapp.common.data.exceptions.ApiPageNotFoundException;
import com.prueba.homeworkapp.common.data.models.ApiPage;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ApiPageMapper<T> {

    public ApiPage<T> entityToDto(final Page<T> page) {
        if (page.getTotalPages() < page.getNumber()) {
            throw new ApiPageNotFoundException(
                    page.getNumber()
            );
        }
        return new ApiPage<>(
                page.getTotalElements(),
                page.getNumberOfElements(),
                page.getNumber(),
                0,
                page.getTotalPages() - 1L,
                page.getContent()
        );
    }
}
