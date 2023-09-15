package com.prueba.homeworkapp.modules.user.models.mappers;

import com.prueba.homeworkapp.modules.user.models.entities.UserJpaEntity;
import com.prueba.homeworkapp.modules.user.models.requests.UserPatchRequest;
import com.prueba.homeworkapp.modules.user.models.requests.UserRequest;
import com.prueba.homeworkapp.modules.user.models.responses.ProfileResponse;
import com.prueba.homeworkapp.modules.user.models.responses.UserResponse;
import com.prueba.homeworkapp.modules.user.models.dtos.User;
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
