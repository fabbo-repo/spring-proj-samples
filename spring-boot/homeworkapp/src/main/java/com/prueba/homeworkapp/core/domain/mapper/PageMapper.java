package com.prueba.homeworkapp.core.domain.mapper;

import com.prueba.homeworkapp.core.domain.responses.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class PageMapper<T> {

    public PageResponse<T> taskPageToResponse(final Page<T> page) {
        return new PageResponse<>(
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
