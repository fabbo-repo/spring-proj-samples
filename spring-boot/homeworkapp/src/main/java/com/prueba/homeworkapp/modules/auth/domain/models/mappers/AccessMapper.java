package com.prueba.homeworkapp.modules.auth.domain.models.mappers;

import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.UserAndJwts;
import com.prueba.homeworkapp.modules.auth.domain.models.requests.AccessRequest;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccessMapper {
    AccessMapper INSTANCE = Mappers.getMapper(AccessMapper.class);

    UserAndJwts userAndJwtsToDto(final User user, final Jwts jwts);

    Access requestToDto(final AccessRequest accessRequest);
}
