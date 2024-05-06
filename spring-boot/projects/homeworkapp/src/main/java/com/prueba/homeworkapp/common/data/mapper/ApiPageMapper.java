package com.prueba.homeworkapp.common.mapper;

import com.prueba.homeworkapp.common.models.ApiPage;
import com.prueba.homeworkapp.common.data.exceptions.ApiPageNotFoundException;
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
                pageToNext(page),
                pageToPrev(page),
                page.getContent()
        );
    }

    private String pageToNext(final Page<T> page) {
        final int totalPages = page.getTotalPages();
        final int currentPage = page.getNumber();
        if (currentPage >= totalPages - 1) {
            return null;
        }
        final ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        builder.queryParam("page", currentPage + 1);
        return builder.build().toUri().toString();
    }

    private String pageToPrev(final Page<T> page) {
        final int currentPage = page.getNumber();
        if (currentPage <= 0) {
            return null;
        }
        final ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        builder.queryParam("page", currentPage - 1);
        return builder.build().toUri().toString();
    }
}
