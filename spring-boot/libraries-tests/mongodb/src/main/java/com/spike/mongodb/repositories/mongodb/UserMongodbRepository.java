package com.spike.mongodb.repositories.mongodb;

import com.spike.mongodb.models.entities.UserCollectionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongodbRepository extends MongoRepository<UserCollectionEntity, String> {
}
