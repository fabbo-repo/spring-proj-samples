package com.prueba.homeworkapp.modules.auth.domain.models.mappers;

import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.UserAndJwts;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccessServiceMapper {
    AccessServiceMapper INSTANCE = Mappers.getMapper(AccessServiceMapper.class);

    UserAndJwts userAndJwtsToDto(final User user, final Jwts jwts);
}
