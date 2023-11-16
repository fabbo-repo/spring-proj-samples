package com.mercadonatest.eanapi.core.models.exceptions;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(final int pageNum) {
        super(String.format("Could not find page %s", pageNum));
    }
}
