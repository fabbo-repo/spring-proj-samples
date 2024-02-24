package com.prueba.homeworkapp.modules.auth.domain.models;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Jwts {
    private String accessToken;

    private String refreshToken;

    private long accessExpiresIn;

    private long refreshExpiresIn;

    public Jwts() {
    }

    public Jwts(
            final String accessToken,
            final String refreshToken,
            final long accessExpiresIn,
            final long refreshExpiresIn
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessExpiresIn = accessExpiresIn;
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setAccessExpiresIn(long accessExpiresIn) {
        this.accessExpiresIn = accessExpiresIn;
    }

    public void setRefreshExpiresIn(long refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Jwts jwts = (Jwts) object;
        return accessExpiresIn == jwts.accessExpiresIn && refreshExpiresIn == jwts.refreshExpiresIn &&
               Objects.equals(accessToken, jwts.accessToken) &&
               Objects.equals(refreshToken, jwts.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, refreshToken, accessExpiresIn, refreshExpiresIn);
    }
}
