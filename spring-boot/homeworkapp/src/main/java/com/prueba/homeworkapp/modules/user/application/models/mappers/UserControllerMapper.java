package com.prueba.homeworkapp.modules.user.application.models.mappers;

import com.prueba.homeworkapp.modules.user.application.models.requests.UserPatchRequest;
import com.prueba.homeworkapp.modules.user.application.models.requests.UserRequest;
import com.prueba.homeworkapp.modules.user.application.models.responses.ProfileResponse;
import com.prueba.homeworkapp.modules.user.application.models.responses.UserResponse;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserControllerMapper {
    UserControllerMapper INSTANCE = Mappers.getMapper(UserControllerMapper.class);

    User requestToDto(final UserRequest userRequest);

    User patchRequestToDto(final UserPatchRequest userRequest);

    UserResponse dtoToResponse(final User user);

    ProfileResponse dtoToProfileResponse(final User user);
}
