package com.prueba.homeworkapp.modules.auth.domain.models;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Refresh {
    private String refreshToken;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Refresh refresh = (Refresh) object;
        return Objects.equals(refreshToken, refresh.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken);
    }
}
