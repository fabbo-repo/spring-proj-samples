package com.prueba.homeworkapp.modules.auth.models.mappers;

import com.prueba.homeworkapp.modules.auth.models.dtos.Refresh;
import com.prueba.homeworkapp.modules.auth.models.requests.RefreshRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RefreshMapper {
    RefreshMapper INSTANCE = Mappers.getMapper(RefreshMapper.class);

    Refresh requestToDto(final RefreshRequest refreshRequest);
}
