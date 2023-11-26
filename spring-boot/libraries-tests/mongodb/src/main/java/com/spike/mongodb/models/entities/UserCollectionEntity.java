package com.spike.mongodb.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldName;

import java.util.List;

@Document(collection = UserCollectionEntity.COLLECTION_NAME)
@Getter
@Setter
public class UserCollectionEntity extends AuditableCollectionEntity {
    public static final String COLLECTION_NAME = "usersCollection";

    public static final String NAME_COLUMN_NAME = "name";
    public static final String AGE_COLUMN_NAME = "age";
    public static final String ROLES_COLUMN_NAME = "role_list";

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(name = NAME_COLUMN_NAME, nameType = FieldName.Type.KEY)
    private String name;

    @Field(name = AGE_COLUMN_NAME, nameType = FieldName.Type.KEY)
    private Integer age;

    @DBRef
    @Field(name = ROLES_COLUMN_NAME, nameType = FieldName.Type.KEY)
    private List<RoleCollectionEntity> roles;
}
