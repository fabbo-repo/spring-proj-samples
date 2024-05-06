package com.prueba.homeworkapp.common.data.exceptions;

import lombok.Getter;

@Getter
public class ApiCodeException extends RuntimeException {
    private final int code;

    public ApiCodeException(final int code, final String message) {
        super(message);
        this.code = code;
    }
}
