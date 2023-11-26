package com.spike.mongodb.models.entities;

import com.spike.mongodb.models.enums.RoleTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldName;

@Document(collection = RoleCollectionEntity.COLLECTION_NAME)
@Getter
@Setter
@Builder
public class RoleCollectionEntity extends AuditableCollectionEntity {
    public static final String COLLECTION_NAME = "rolesCollection";

    public static final String ROLE_TYPE_COLUMN_NAME = "roleType";

    @Id
    private String id;

    @Field(
            name = ROLE_TYPE_COLUMN_NAME,
            nameType = FieldName.Type.KEY
    )
    private RoleTypeEnum type;
}
