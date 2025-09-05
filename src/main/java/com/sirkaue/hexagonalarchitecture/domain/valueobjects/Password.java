package com.sirkaue.hexagonalarchitecture.domain.valueobjects;

import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.InvalidPasswordException;

public record Password(String value) {

    public Password {
        if (value == null || value.length() < 6) {
            throw new InvalidPasswordException("Password must be at least 6 characters");
        }
    }
}
