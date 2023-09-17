package com.prueba.homeworkapp.modules.auth.domain.services;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.prueba.homeworkapp.modules.auth.infrastructure.clients.AuthAdminClient;
import com.prueba.homeworkapp.modules.auth.infrastructure.clients.AuthClient;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Refresh;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.UserAndJwts;
import com.prueba.homeworkapp.modules.auth.domain.models.mappers.AccessMapper;
import com.prueba.homeworkapp.modules.auth.domain.models.mappers.RegisterMapper;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;

    private final AuthAdminClient authAdminClient;

    private final UserRepository userRepository;

    private final RegisterMapper registerMapper = RegisterMapper.INSTANCE;

    private final AccessMapper accessMapper = AccessMapper.INSTANCE;

    @Override
    public UserAndJwts access(final Access access) {
        final Jwts jwts = authClient.access(
                access.getEmail(),
                access.getPassword()
        );
        final JsonObject decodedAccessToken = authClient.decodeToken(
                jwts.getAccessToken()
        );
        final UUID userId = UUID.fromString(
                decodedAccessToken.get("sub").getAsString()
        );
        final User user = userRepository.findById(
                userId,
                () -> userRepository.save(
                        User.builder()
                            .id(userId)
                            .email(access.getEmail())
                            .username(access.getEmail().split("@")[0])
                            .firstName("")
                            .lastName("")
                            .build()
                )
        );
        return accessMapper.userAndJwtsToDto(user, jwts);
    }

    @Override
    public Jwts refresh(final Refresh refresh) {
        return authClient.refresh(refresh.getRefreshToken());
    }

    @Override
    public void logout(final Refresh refresh) {
        authClient.logout(
                refresh.getRefreshToken()
        );
    }
}
