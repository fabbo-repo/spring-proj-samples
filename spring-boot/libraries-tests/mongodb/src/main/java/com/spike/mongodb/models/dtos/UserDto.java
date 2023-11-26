package com.spike.mongodb.models.dtos;

import com.spike.mongodb.models.enums.RoleTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private String id;

    private String name;

    private Integer age;

    private List<RoleTypeEnum> roles;
}
