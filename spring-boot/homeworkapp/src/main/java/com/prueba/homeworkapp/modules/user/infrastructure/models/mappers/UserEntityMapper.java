package com.prueba.homeworkapp.modules.user.infrastructure.models.mappers;

import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.infrastructure.models.entities.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityMapper {
    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    UserJpaEntity dtoToEntity(final User user);

    User entityToDto(final UserJpaEntity userJpaEntity);
}
