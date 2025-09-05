package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

public class NameTooLongException extends RuntimeException {

    public NameTooLongException(String message) {
        super(message);
    }
}
