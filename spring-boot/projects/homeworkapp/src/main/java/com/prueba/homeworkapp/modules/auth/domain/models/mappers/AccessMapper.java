package com.prueba.homeworkapp.modules.auth.domain.models.mappers;

import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.requests.AccessRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccessMapper {
    AccessMapper INSTANCE = Mappers.getMapper(AccessMapper.class);

    Access requestToDto(final AccessRequest accessRequest);
}
