package com.prueba.homeworkapp.commons.mapper;

import com.prueba.homeworkapp.commons.models.ApiPage;
import com.prueba.homeworkapp.commons.exceptions.PageNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ApiPageMapper<T> {

    public ApiPage<T> entityToDto(final Page<T> page) {
        if (page.getTotalPages() < page.getNumber()) {
            throw new PageNotFoundException(
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
