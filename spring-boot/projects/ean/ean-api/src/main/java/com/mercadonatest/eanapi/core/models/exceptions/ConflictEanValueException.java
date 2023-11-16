package com.mercadonatest.eanapi.core.models.exceptions;

public class ConflictEanValueException extends RuntimeException {
    public ConflictEanValueException() {
        super("Conflict with stored ean value");
    }
}
