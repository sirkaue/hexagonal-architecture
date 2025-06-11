package com.sirkaue.hexagonalarchitecture.domain.valueobjects;

import com.sirkaue.hexagonalarchitecture.domain.exception.InvalidEmailException;

public record Email(String value) {

    public Email {
        if (value == null || !value.matches("[^@]+@[^\\.]+\\..+")) {
            throw new InvalidEmailException("Invalid email format");
        }
        value = value.toLowerCase();
    }
}
