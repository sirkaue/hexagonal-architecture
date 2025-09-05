package com.sirkaue.hexagonalarchitecture.domain.valueobjects;

import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.NameBlankException;
import com.sirkaue.hexagonalarchitecture.domain.exception.exceptions.NameTooLongException;

public record Name(String value) {

    public Name {
        if (value == null || value.isBlank()) {
            throw new NameBlankException();
        }

        value = value.trim();

        if (value.length() > 100) {
            throw new NameTooLongException();
        }
    }
}
