package com.prueba.homeworkapp.modules.auth.application.models.mappers;

import com.prueba.homeworkapp.modules.auth.application.models.requests.RefreshRequest;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Refresh;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RefreshControllerMapper {
    RefreshControllerMapper INSTANCE = Mappers.getMapper(RefreshControllerMapper.class);

    Refresh requestToDto(final RefreshRequest refreshRequest);
}
