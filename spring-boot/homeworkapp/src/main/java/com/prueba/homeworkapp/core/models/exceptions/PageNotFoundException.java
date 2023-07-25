package com.prueba.homeworkapp.core.models.exceptions;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(final int pageNum, final String idName) {
        super(String.format("Could not find page %s for %s", pageNum, idName));
    }
}