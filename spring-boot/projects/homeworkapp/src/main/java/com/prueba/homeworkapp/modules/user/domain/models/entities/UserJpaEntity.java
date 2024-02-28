package com.prueba.homeworkapp.modules.user.domain.models.entities;

import com.prueba.homeworkapp.common.entities.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = UserJpaEntity.TABLE_NAME)
public class UserJpaEntity extends Auditable<String> {
    public static final String TABLE_NAME = "app_user";

    public static final String SORT_FIELD = "createdAt";
    public static final String ID_COL = "id";
    public static final String USERNAME_COL = "username";
    public static final String EMAIL_COL = "email";
    public static final String FIRST_NAME_COL = "first_name";
    public static final String LAST_NAME_COL = "last_name";
    public static final String AGE_COL = "age";

    @Id
    @Column(name = ID_COL)
    private UUID id;

    @Column(name = USERNAME_COL, nullable = false)
    private String username;

    @Column(name = EMAIL_COL, nullable = false)
    private String email;

    @Column(name = FIRST_NAME_COL, nullable = false)
    private String firstName;

    @Column(name = LAST_NAME_COL, nullable = false)
    private String lastName;

    @Column(name = AGE_COL)
    private Integer age;
}
