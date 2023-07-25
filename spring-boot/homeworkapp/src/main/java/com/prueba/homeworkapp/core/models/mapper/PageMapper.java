package com.prueba.homeworkapp.core.models.mapper;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.core.models.exceptions.PageNotFoundException;
import com.prueba.homeworkapp.modules.task.infrastructure.models.entities.TaskJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class PageMapper<T> {

    public PageDto<T> enityToDto(final Page<T> page) {
        if (page.getTotalPages() < page.getNumber()) {
            throw new PageNotFoundException(
                    page.getNumber(),
                    TaskJpaEntity.TABLE_NAME
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
