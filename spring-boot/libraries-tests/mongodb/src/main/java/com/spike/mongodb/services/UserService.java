package com.spike.mongodb.services;

import com.spike.mongodb.models.dtos.UserDto;
import com.spike.mongodb.models.entities.RoleCollectionEntity;
import com.spike.mongodb.models.entities.UserCollectionEntity;
import com.spike.mongodb.models.mappers.UserMapper;
import com.spike.mongodb.models.requests.UserRequest;
import com.spike.mongodb.repositories.mongodb.RoleRepository;
import com.spike.mongodb.repositories.mongodb.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserDto getUser(final String id) {
        final UserCollectionEntity savedEntity = userRepository
                .findById(id)
                .orElseThrow(
                        RuntimeException::new
                );
        return userMapper.entityToDto(savedEntity);
    }

    public UserDto createUser(final UserRequest request) {
        final UserCollectionEntity entity = userMapper.requestToEntity(request);
        entity.setRoles(
                request.roles().stream().map(
                        (roleTypeEnum) ->
                                roleRepository
                                        .findByType(roleTypeEnum)
                                        .orElse(
                                                roleRepository.save(
                                                        RoleCollectionEntity
                                                                .builder()
                                                                .type(roleTypeEnum)
                                                                .build()
                                                )
                                        )
                ).toList()
        );
        final UserCollectionEntity savedEntity = userRepository.save(entity);
        return userMapper.entityToDto(savedEntity);
    }

    public List<UserDto> searchUser(
            final String name,
            final Integer age
    ) {
        final List<UserCollectionEntity> entityList = userRepository
                .search(
                        name,
                        age
                );
        return entityList
                .stream()
                .map(userMapper::entityToDto)
                .toList();
    }
}
