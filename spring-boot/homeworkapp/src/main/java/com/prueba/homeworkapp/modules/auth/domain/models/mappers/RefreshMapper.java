package com.prueba.homeworkapp.modules.auth.domain.models.mappers;

import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Refresh;
import com.prueba.homeworkapp.modules.auth.domain.models.requests.RefreshRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RefreshMapper {
    RefreshMapper INSTANCE = Mappers.getMapper(RefreshMapper.class);

    Refresh requestToDto(final RefreshRequest refreshRequest);
}
