package com.prueba.homeworkapp.modules.user.domain.models.entities;

import com.prueba.homeworkapp.core.models.entities.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    public final static String TABLE_NAME = "app_user";

    public final static String SORT_FIELD = "createdAt";
    public final static String ID_COL = "id";
    public final static String USERNAME_COL = "username";
    public final static String EMAIL_COL = "email";
    public final static String FIRST_NAME_COL = "first_name";
    public final static String LAST_NAME_COL = "last_name";
    public final static String AGE_COL = "age";

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
