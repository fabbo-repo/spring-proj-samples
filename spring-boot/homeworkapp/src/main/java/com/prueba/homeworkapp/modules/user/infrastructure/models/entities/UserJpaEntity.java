package com.prueba.homeworkapp.modules.user.infrastructure.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = UserJpaEntity.TABLE_NAME)
public class UserJpaEntity {
    public final static String TABLE_NAME = "app_user";
    public final static String SORT_FIELD = "createdAt";
    public final static String ID_COL = "id";
    public final static String USERNAME_COL = "username";
    public final static String EMAIL_COL = "email";
    public final static String FIRST_NAME_COL = "first_name";
    public final static String LAST_NAME_COL = "last_name";
    public final static String AGE_COL = "age";
    public final static String CREATED_AT_COL = "created_at";
    private final static String CREATED_BY_COL = "created_by";
    private final static String UPDATED_AT_COL = "updated_at";
    private final static String UPDATED_BY_COL = "updated_by";

    @Id
    @Column(name = ID_COL)
    private UUID id;

    @NotBlank
    @Size(max = 30)
    @Column(name = USERNAME_COL)
    private String username;

    @NotNull
    @Email
    @Column(name = EMAIL_COL)
    private String email;

    @NotBlank
    @Size(max = 50)
    @Column(name = FIRST_NAME_COL)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    @Column(name = LAST_NAME_COL)
    private String lastName;

    @Min(0)
    @Min(200)
    @Column(name = AGE_COL)
    private Integer age;

    @CreatedDate
    @Column(name = CREATED_AT_COL, updatable = false)
    private LocalDateTime createdAt; // audit field

    @CreatedBy
    @Column(name = CREATED_BY_COL, updatable = false)
    private String createdBy; // audit field

    @LastModifiedDate
    @Column(name = UPDATED_AT_COL, insertable = false)
    private LocalDateTime updatedAt; // audit field

    @LastModifiedBy
    @Column(name = UPDATED_BY_COL, insertable = false)
    private String updatedBy; // audit field
}
