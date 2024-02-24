package com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.mappers;

import com.prueba.homeworkapp.modules.auth.domain.models.Access;
import com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.requests.AccessRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccessRestMapper {
    AccessRestMapper INSTANCE = Mappers.getMapper(AccessRestMapper.class);

    Access accessRequestToAccess(final AccessRequest accessRequest);
}
