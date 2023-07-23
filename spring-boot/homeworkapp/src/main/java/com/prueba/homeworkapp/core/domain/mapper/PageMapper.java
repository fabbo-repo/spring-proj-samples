package com.prueba.homeworkapp.core.domain.mapper;

import com.prueba.homeworkapp.core.domain.responses.PageResponse;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Mapper
public interface PageMapper {
    PageMapper INSTANCE = Mappers.getMapper(PageMapper.class);

    @Mapping(target = "count", source = "totalElements")
    @Mapping(target = "results", source = "content")
    @Mapping(target = "next", source = "page", qualifiedByName = "pageToNext")
    @Mapping(target = "previous", source = "page", qualifiedByName = "pageToPrev")
    PageResponse<TaskResponse> taskPageToResponse(final Page<TaskResponse> page);

    @Named("pageToNext")
    default String pageToNext(final Page<TaskResponse> page) {
        final int totalPages = page.getTotalPages();
        final int currentPage = page.getNumber();
        if (currentPage >= totalPages - 1) {
            return null;
        }
        final ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        builder.queryParam("page", currentPage + 1);
        return builder.build().toUri().toString();
    }

    @Named("pageToPrev")
    default String pageToPrev(final Page<TaskResponse> page) {
        final int currentPage = page.getNumber();
        if (currentPage <= 0) {
            return null;
        }
        final ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        builder.queryParam("page", currentPage - 1);
        return builder.build().toUri().toString();
    }
}
