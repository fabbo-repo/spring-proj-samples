package com.spike.mongodb.repositories.mongodb;

import com.spike.mongodb.models.entities.UserCollectionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserMongodbRepository userMongodbRepository;

    private final MongoTemplate mongoTemplate;

    public UserCollectionEntity save(final UserCollectionEntity entity) {
        return userMongodbRepository.save(
                entity
        );
    }

    public Optional<UserCollectionEntity> findById(final String id) {
        return userMongodbRepository
                .findById(id);
    }

    public List<UserCollectionEntity> search(
            final String name,
            final Integer age
    ) {
        final Query query = new Query();
        if (name != null) {
            query.addCriteria(
                    Criteria
                            .where(UserCollectionEntity.NAME_FIELD_NAME)
                            .is(name)
            );
        }
        if (age != null) {
            query.addCriteria(
                    Criteria
                            .where(UserCollectionEntity.AGE_FIELD_NAME)
                            .is(age)
            );
        }
        return mongoTemplate.find(
                query,
                UserCollectionEntity.class
        );
    }
}
