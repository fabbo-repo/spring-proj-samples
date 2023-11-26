package com.spike.mongodb.models.mappers;

import com.spike.mongodb.models.dtos.UserDto;
import com.spike.mongodb.models.entities.RoleCollectionEntity;
import com.spike.mongodb.models.entities.UserCollectionEntity;
import com.spike.mongodb.models.enums.RoleTypeEnum;
import com.spike.mongodb.models.requests.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", ignore = true)
    UserCollectionEntity requestToEntity(final UserRequest request);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleEntityToEnum")
    UserDto entityToDto(final UserCollectionEntity entity);

    @Named("roleEntityToEnum")
    default List<RoleTypeEnum> roleEntityToEnum(List<RoleCollectionEntity> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return Collections.emptyList();
        }
        return entityList.stream().map(
                RoleCollectionEntity::getType
        ).toList();
    }
}
