package com.prueba.homeworkapp.modules.auth.domain.models.mappers;

import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Refresh;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Register;
import com.prueba.homeworkapp.modules.auth.domain.models.requests.RefreshRequest;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

@Mapper
public interface RegisterMapper {
    RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);
    String USER_CREDENTIALS_TYPE = "password";
    boolean USER_CREDENTIALS_TEMPORARY = false;

    Refresh requestToDto(final RefreshRequest refreshRequest);

    @Mapping(
            target = "credentials",
            expression = "java(passwordCredentials(register))"
    )
    @Mapping(target = "emailVerified", expression = "java(true)")
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "self", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTimestamp", ignore = true)
    @Mapping(target = "totp", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    UserRepresentation dtoToRepresentation(final Register register);

    default List<CredentialRepresentation> passwordCredentials(final Register register) {
        final CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setTemporary(USER_CREDENTIALS_TEMPORARY);
        credentials.setType(USER_CREDENTIALS_TYPE);
        credentials.setValue(register.getPassword());
        return Collections.singletonList(credentials);
    }
}
