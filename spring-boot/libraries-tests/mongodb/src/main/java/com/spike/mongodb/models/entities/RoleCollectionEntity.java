package com.spike.mongodb.models.entities;

import com.spike.mongodb.models.enums.RoleTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldName;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = RoleCollectionEntity.COLLECTION_NAME)
@Getter
@Setter
@Builder
public class RoleCollectionEntity extends AuditableCollectionEntity<String> {
    public static final String COLLECTION_NAME = "rolesCollection";

    public static final String ROLE_TYPE_FIELD_NAME = "roleType";

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(
            name = ROLE_TYPE_FIELD_NAME,
            nameType = FieldName.Type.KEY,
            targetType = FieldType.STRING
    )
    private RoleTypeEnum type;
}
