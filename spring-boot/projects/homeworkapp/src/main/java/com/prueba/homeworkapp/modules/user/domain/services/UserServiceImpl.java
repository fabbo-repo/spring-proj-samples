package com.prueba.homeworkapp.modules.user.domain.services;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.prueba.homeworkapp.common.clients.keycloak.KeycloakAdminClient;
import com.prueba.homeworkapp.common.exceptions.EntityNotFoundException;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.domain.models.entities.UserJpaEntity;
import com.prueba.homeworkapp.modules.user.domain.models.mappers.UserMapper;
import com.prueba.homeworkapp.modules.user.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final KeycloakAdminClient keycloakAdminClient;

    private static final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public User getUser(UUID id) {
        final UserJpaEntity userJpaEntity = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                UserJpaEntity.TABLE_NAME,
                                UserJpaEntity.ID_COL,
                                id.toString()
                        )
                );
        return userMapper.entityToDto(userJpaEntity);
    }

    @Override
    public void createUserIfNotExists(
            final Jwt jwt
    ) {
        final UUID userId = UUID.fromString(jwt.getSubject());
        final boolean existsById = userRepository.existsById(userId);
        if (!existsById) {
            final JsonObject decodedToken = decodeToken(jwt.getTokenValue());
            final String email = decodedToken.get("email").getAsString();
            userRepository.save(
                    UserJpaEntity
                            .builder()
                            .id(userId)
                            .email(email)
                            .username(email.split("@")[0])
                            .firstName("")
                            .lastName("")
                            .build()
            );
        }
    }

    @Override
    public void updateUser(final User user) {
        final UserJpaEntity dbUser = userRepository
                .findById(user.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                UserJpaEntity.TABLE_NAME,
                                UserJpaEntity.ID_COL,
                                user.getId().toString()
                        )
                );
        dbUser.setUsername(user.getUsername());
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setAge(user.getAge());
        userRepository.save(dbUser);
    }

    @Override
    public void patchUser(User user) {
        // Unimplemented
    }

    @Override
    public void deleteUser(UUID id) {
        // Unimplemented
    }

    private JsonObject decodeToken(final String token) {
        final String[] chunks = token.split("\\.");
        final Base64.Decoder decoder = Base64.getUrlDecoder();
        final String payload = new String(decoder.decode(chunks[1]), StandardCharsets.UTF_8);
        return JsonParser.parseString(payload).getAsJsonObject();
    }
}
