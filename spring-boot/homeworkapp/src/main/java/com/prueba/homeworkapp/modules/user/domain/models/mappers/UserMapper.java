package com.prueba.homeworkapp.modules.user.domain.models.mappers;

import com.prueba.homeworkapp.modules.user.domain.models.dtos.UserDto;
import com.prueba.homeworkapp.modules.user.domain.models.requests.UserRequest;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    String USER_CREDENTIALS_TYPE = "password";
    boolean USER_CREDENTIALS_TEMPORARY = false;

    UserDto requestToDto(final UserRequest userRequest);

    @Mapping(target = "credentials", source = "userDto", qualifiedByName = "passwordCredentials")
    @Mapping(target = "emailVerified", defaultValue = "true")
    @Mapping(target = "enabled", defaultValue = "true")
    UserRepresentation dtoToRepresentation(final UserDto userDto);

    @Named("passwordCredentials")
    default List<CredentialRepresentation> passwordCredentials(final UserDto userDto) {
        final CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setTemporary(USER_CREDENTIALS_TEMPORARY);
        credentials.setType(USER_CREDENTIALS_TYPE);
        credentials.setValue(userDto.getPassword());
        return Collections.singletonList(credentials);
    }

    UserDto representationToDto(final UserRepresentation userRepresentation);
}
