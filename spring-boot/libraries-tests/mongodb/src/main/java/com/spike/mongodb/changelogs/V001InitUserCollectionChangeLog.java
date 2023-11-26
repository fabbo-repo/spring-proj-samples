package com.spike.mongodb.changelogs;

import com.mongodb.client.model.IndexOptions;
import com.spike.mongodb.models.entities.UserCollectionEntity;
import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;

@ChangeUnit(id = "init-user-collection", order = "001")
public class V001InitUserCollectionChangeLog {

    @BeforeExecution
    public void before(final MongoTemplate mongoTemplate) {
        mongoTemplate
                .createCollection(
                        UserCollectionEntity.COLLECTION_NAME,
                        CollectionOptions
                                .empty()
                                .validator(
                                        Validator.schema(
                                                MongoJsonSchema
                                                        .builder()
                                                        .required(
                                                                UserCollectionEntity.NAME_FIELD_NAME,
                                                                UserCollectionEntity.AGE_FIELD_NAME
                                                        )
                                                        .properties(
                                                                JsonSchemaProperty.string(
                                                                        UserCollectionEntity.NAME_FIELD_NAME
                                                                ),
                                                                JsonSchemaProperty.int32(
                                                                        UserCollectionEntity.AGE_FIELD_NAME
                                                                )
                                                        ).build()))
                )
                .createIndex(
                        new Document(
                                UserCollectionEntity.NAME_FIELD_NAME,
                                1 // Ascending index
                        ),
                        new IndexOptions()
                                .name(UserCollectionEntity.NAME_FIELD_NAME)
                                .unique(true)
                );
    }

    @Execution
    public void migrationMethod() {
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
    }

    @RollbackExecution
    public void rollback() {
    }
}
