package com.sirkaue.hexagonalarchitecture.domain.valueobjects;

import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.InvalidNameException;

public record Name(String value) {

    public Name {
        if (value == null || value.isBlank()) {
            throw new InvalidNameException("Name cannot be null or blank");
        }

        value = value.trim();

        if (value.length() > 100) {
            throw new InvalidNameException("Name must not exceed 100 characters");
        }
    }
}
