package com.prueba.homeworkapp.common.data.exceptions;

public class ApiPageNotFoundException extends RuntimeException {
    public ApiPageNotFoundException(final int pageNum) {
        super(String.format("Could not find page %s", pageNum));
    }
}
