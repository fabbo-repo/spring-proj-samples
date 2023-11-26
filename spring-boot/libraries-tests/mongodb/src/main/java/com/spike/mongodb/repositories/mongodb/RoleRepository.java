package com.spike.mongodb.repositories.mongodb;

import com.spike.mongodb.models.entities.RoleCollectionEntity;
import com.spike.mongodb.models.enums.RoleTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepository {
    private final RoleMongodbRepository roleMongodbRepository;

    public RoleCollectionEntity save(final RoleCollectionEntity entity) {
        return roleMongodbRepository.save(
                entity
        );
    }

    public Optional<RoleCollectionEntity> findByType(final RoleTypeEnum roleTypeEnum) {
        return roleMongodbRepository
                .findByType(roleTypeEnum)
                .stream().findFirst();
    }
}
