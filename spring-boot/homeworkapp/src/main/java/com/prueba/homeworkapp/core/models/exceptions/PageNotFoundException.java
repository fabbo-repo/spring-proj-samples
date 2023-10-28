package com.prueba.homeworkapp.core.models.exceptions;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(final int pageNum) {
        super(String.format("Could not find page %s", pageNum));
    }
}