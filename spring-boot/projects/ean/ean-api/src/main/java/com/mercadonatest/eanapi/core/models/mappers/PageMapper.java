package com.mercadonatest.eanapi.core.models.mappers;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.core.models.exceptions.PageNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class PageMapper<T> {

    public PageDto<T> entityToDto(final Page<T> page) {
        if (page.getTotalPages() < page.getNumber()) {
            throw new PageNotFoundException(
                    page.getNumber()
            );
        }
        return new PageDto<>(
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