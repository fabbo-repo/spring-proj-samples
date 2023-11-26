package com.spike.mongodb.models.requests;

import com.spike.mongodb.models.enums.RoleTypeEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record UserRequest(
        @NotBlank
        @Size(min = 3, max = 30)
        String name,
        @Min(18)
        @Max(200)
        Integer age,
        List<RoleTypeEnum> roles
) {
}
