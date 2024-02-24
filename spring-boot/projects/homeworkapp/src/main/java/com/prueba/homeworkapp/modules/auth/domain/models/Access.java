package com.prueba.homeworkapp.modules.auth.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class Access {
    private String email;

    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Access access = (Access) object;
        return Objects.equals(email, access.email) && Objects.equals(password, access.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
