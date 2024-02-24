package com.prueba.homeworkapp.modules.auth.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class Register {
    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Register register = (Register) object;
        return Objects.equals(username, register.username) && Objects.equals(email, register.email) &&
               Objects.equals(password, register.password) &&
               Objects.equals(firstName, register.firstName) &&
               Objects.equals(lastName, register.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, firstName, lastName);
    }
}
