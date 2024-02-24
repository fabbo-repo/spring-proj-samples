package com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.mappers;

import com.prueba.homeworkapp.modules.auth.domain.models.Refresh;
import com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.requests.RefreshRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RefreshRestMapper {
    RefreshRestMapper INSTANCE = Mappers.getMapper(RefreshRestMapper.class);

    Refresh refreshRequestToRefresh(final RefreshRequest refreshRequest);
}
