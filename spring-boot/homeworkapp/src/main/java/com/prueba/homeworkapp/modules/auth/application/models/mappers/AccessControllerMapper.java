package com.prueba.homeworkapp.modules.auth.application.models.mappers;

import com.prueba.homeworkapp.modules.auth.application.models.requests.AccessRequest;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Access;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccessControllerMapper {
    AccessControllerMapper INSTANCE = Mappers.getMapper(AccessControllerMapper.class);

    Access requestToDto(final AccessRequest accessRequest);
}
