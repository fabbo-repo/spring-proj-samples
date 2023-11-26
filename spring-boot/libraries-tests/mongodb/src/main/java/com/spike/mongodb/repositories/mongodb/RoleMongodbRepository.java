package com.spike.mongodb.repositories.mongodb;

import com.spike.mongodb.models.entities.RoleCollectionEntity;
import com.spike.mongodb.models.enums.RoleTypeEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleMongodbRepository extends MongoRepository<RoleCollectionEntity, String> {
    List<RoleCollectionEntity> findByType(RoleTypeEnum type);
}
