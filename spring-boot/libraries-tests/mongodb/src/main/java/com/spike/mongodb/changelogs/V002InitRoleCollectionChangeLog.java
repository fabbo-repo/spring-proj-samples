package com.spike.mongodb.changelogs;

import com.mongodb.client.model.IndexOptions;
import com.spike.mongodb.models.entities.RoleCollectionEntity;
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

@ChangeUnit(id = "init-role-collection", order = "002")
public class V002InitRoleCollectionChangeLog {

    @BeforeExecution
    public void before(final MongoTemplate mongoTemplate) {
        mongoTemplate
                .createCollection(
                        RoleCollectionEntity.COLLECTION_NAME,
                        CollectionOptions
                                .empty()
                                .validator(
                                        Validator.schema(
                                                MongoJsonSchema
                                                        .builder()
                                                        .required(
                                                                RoleCollectionEntity.ROLE_TYPE_FIELD_NAME
                                                        )
                                                        .properties(
                                                                JsonSchemaProperty.string(
                                                                        RoleCollectionEntity.ROLE_TYPE_FIELD_NAME
                                                                )
                                                        ).build()))
                )
                .createIndex(
                        new Document(
                                RoleCollectionEntity.ROLE_TYPE_FIELD_NAME,
                                1 // Ascending index
                        ),
                        new IndexOptions()
                                .name(RoleCollectionEntity.ROLE_TYPE_FIELD_NAME)
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
