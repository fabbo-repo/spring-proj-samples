package com.prueba.homeworkapp.commons.config;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public class ReplaceUnderscoresAndCamelCase extends DisplayNameGenerator.Standard {
    @Override
    public String generateDisplayNameForClass(final Class<?> testClass) {
        return replaceForClass(super.generateDisplayNameForClass(testClass));
    }

    @Override
    public String generateDisplayNameForNestedClass(final Class<?> nestedClass) {
        return replaceForClass(super.generateDisplayNameForNestedClass(nestedClass));
    }

    @Override
    public String generateDisplayNameForMethod(
            final Class<?> testClass,
            final Method testMethod
    ) {
        return replaceForMethod(super.generateDisplayNameForMethod(testClass, testMethod));
    }

    private String replaceForClass(final String name) {
        final StringBuilder result = new StringBuilder();
        result.append(name.charAt(0));
        for (int i = 1; i < name.length(); i++) {
            if (Character.isUpperCase(name.charAt(i))) {
                result.append(' ');
            }
            result.append(name.charAt(i));
        }
        return result.toString();
    }

    private String replaceForMethod(String name) {
        StringBuilder result = new StringBuilder();
        result.append(Character.toUpperCase(name.charAt(0)));
        for (int i = 1; i < name.length(); i++) {
            if (Character.isUpperCase(name.charAt(i))
                || Character.isDigit(name.charAt(i))) {
                if (!Character.isDigit(name.charAt(i - 1))
                    || !Character.isDigit(name.charAt(i))) {
                    result.append(' ');
                }
                result.append(Character.toLowerCase(name.charAt(i)));
            } else if ('_' == name.charAt(i)) {
                result.append(' ');
            } else if (name.charAt(i) != '('
                       && name.charAt(i) != ')') {
                result.append(name.charAt(i));
            }
        }
        return result.toString();
    }
}
