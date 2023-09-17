package com.prueba.homeworkapp.modules.user.domain.models.mappers;

import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.domain.models.requests.UserPatchRequest;
import com.prueba.homeworkapp.modules.user.domain.models.requests.UserRequest;
import com.prueba.homeworkapp.modules.user.domain.models.responses.ProfileResponse;
import com.prueba.homeworkapp.modules.user.domain.models.entities.UserJpaEntity;
import com.prueba.homeworkapp.modules.user.domain.models.responses.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    User requestToDto(final UserRequest userRequest);

    @Mapping(target = "id", ignore = true)
    User patchRequestToDto(final UserPatchRequest userRequest);

    UserResponse dtoToResponse(final User user);

    ProfileResponse dtoToProfileResponse(final User user);

    UserJpaEntity dtoToEntity(final User user);

    User entityToDto(final UserJpaEntity userJpaEntity);
}
