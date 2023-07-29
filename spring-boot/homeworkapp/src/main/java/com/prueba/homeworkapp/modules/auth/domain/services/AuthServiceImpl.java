package com.prueba.homeworkapp.modules.auth.domain.services;

import com.prueba.homeworkapp.modules.auth.domain.clients.AuthAdminClient;
import com.prueba.homeworkapp.modules.auth.domain.clients.AuthClient;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Refresh;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Register;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.UserAndJwts;
import com.prueba.homeworkapp.modules.auth.domain.models.mappers.AccessServiceMapper;
import com.prueba.homeworkapp.modules.auth.domain.models.mappers.RegisterServiceMapper;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;

    private final AuthAdminClient authAdminClient;

    private final UserRepository userRepository;

    private final RegisterServiceMapper registerMapper = RegisterServiceMapper.INSTANCE;

    private final AccessServiceMapper accessMapper = AccessServiceMapper.INSTANCE;

    @Override
    public UserAndJwts access(final Access access) {
        final Jwts jwts = authClient.access(
                access.getEmail(),
                access.getPassword()
        );
        final User user = userRepository.save(
                User.builder()
                    .email(access.getEmail())
                    .username(access.getEmail().split("@")[0])
                    .firstName("")
                    .lastName("")
                    .build()
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

    @Override
    public void register(final Register register) {
        authAdminClient.createUser(
                registerMapper.dtoToRepresentation(register)
        );
    }
}
