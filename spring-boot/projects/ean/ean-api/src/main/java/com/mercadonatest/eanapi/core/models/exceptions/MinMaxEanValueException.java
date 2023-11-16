package com.mercadonatest.eanapi.core.models.exceptions;

public class MinMaxEanValueException extends RuntimeException {
    public MinMaxEanValueException() {
        super("Min and max ean value are not correct");
    }
}
