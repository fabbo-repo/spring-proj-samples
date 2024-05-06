package com.copeo.copeoapi.common.data.exceptions;

public class ApiPageNotFoundException extends RuntimeException {
    public ApiPageNotFoundException(final int pageNum) {
        super(String.format("Could not find page %s", pageNum));
    }
}
